/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ReseteaPassword.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.dto.AlumnoDTO;
import em.common.dto.CoordinadorDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.service.CoordinatorServiceManager;
import em.service.StudentServiceManager;
import em.util.EmailUtility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static em.util.Hasher.getSHA512;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReseteaPassword", urlPatterns = {"/reset_password"})
public class ReseteaPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest _request, HttpServletResponse _response)
            throws ServletException, IOException {

        HttpSession session = _request.getSession(false);

        String correo = _request.getParameter("email");
        String subject = "Tu contraseña se he reseteado";

        StudentServiceManager studentServiceManager = StudentServiceManager.getInstance();
        AlumnoDTO alumno = studentServiceManager.getAlumnoByEmail(correo, session);
        CoordinatorServiceManager coordinatorServiceManager = CoordinatorServiceManager.getInstance();
        CoordinadorDTO coordinador = coordinatorServiceManager.getCoordinadorByEmail(correo, session);

        String newPassword = studentServiceManager.generateAlumnoPwd(session);

        if ((coordinador == null) && (alumno == null)) {
            session.setAttribute("error", ErrorMessages.ERROR_RESET_EMAIL_NOT_FOUND.toString());
            _response.sendRedirect(_response.encodeRedirectURL("passwordOlvidada.jsp"));
        } else {
            if (alumno != null) {
                alumno.setHashContrasenya(getSHA512(newPassword));
                studentServiceManager.editAlumno(alumno, session);
            } else if (coordinador != null) {
                coordinador.setHashContrasenya(getSHA512(newPassword));
                coordinatorServiceManager.editCoordinador(coordinador, session);
            }

            String content = "¡Hola! Esta es tu nueva contraseña: " + newPassword;
            content += "\nNota: por razones de seguridad, "
                    + "deberías de cambiar tu contraseña en tu próximo inicio de sesión.";

            String message = "";

            try {
                EmailUtility emailUtility = new EmailUtility();
                emailUtility.sendEmail(correo, subject, content);
                session.setAttribute("exitoReset", SuccessMessages.EXITO_RESET_PASSWORD.toString());
                _response.sendRedirect(_response.encodeRedirectURL("passwordOlvidada.jsp"));
            } catch (IOException | MessagingException ex) {
                session.setAttribute("error", ErrorMessages.ERROR_SENDING_EMAIL.toString());
                _response.sendRedirect(_response.encodeRedirectURL("passwordOlvidada.jsp"));
            }
        }
    }
}

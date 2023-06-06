/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AddCoordinador.java
    Date: 5 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.servlet;

import em.common.dto.CoordinadorDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.TipoIdentificacion;
import em.service.CoordinatorServiceManager;
import em.util.EmailUtility;
import em.util.Hasher;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddCoordinador", urlPatterns = {"/addCoordinador"})
public class AddCoordinador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param _request servlet request
     * @param _response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest _request, HttpServletResponse _response)
            throws ServletException, IOException {
        _response.setContentType("text/html;charset=UTF-8");
        _request.setCharacterEncoding("UTF-8");

        HttpSession session = _request.getSession();

        String coordinatorIdentification = _request.getParameter("coordinatorIdentification");

        if (!coordinatorIdentification.isBlank()) {
            CoordinatorServiceManager coordinatorServiceManager = CoordinatorServiceManager.getInstance();
            CoordinadorDTO coordinatorRetrived = coordinatorServiceManager.getCoordinador(coordinatorIdentification, session);
            if (coordinatorRetrived.getNumIdentificacion().length() == 0) { //Comprueba que no existe un coordinador con el mismo número de identificación
                session.setAttribute("errorCoordinador", null); //Resetear variable por anterior comprobación
                
                //Datos del nuevo coordinador a insertar en el sistema
                String coordinatorIdentificationType = _request.getParameter("identificationType");
                String coordinatorName = _request.getParameter("coordinatorName");
                String coordinatorSurname1 = _request.getParameter("coordinatorSurname1");
                String coordinatorSurname2 = _request.getParameter("coordinatorSurname2");
                String coordinatorEmail = _request.getParameter("coordinatorEmail");
                String coordinatorDefaultPassword = _request.getParameter("coordinatorDefaultPassword");
                Integer coordinatorPhone = Integer.valueOf(_request.getParameter("phone"));
                
                // Para coordinadores que no tienen dos apellidos
                if(coordinatorSurname2 == null){
                    coordinatorSurname2 = "";
                }
                
                if (!coordinatorName.isBlank()
                    && !coordinatorSurname1.isBlank() 
                    && !coordinatorEmail.isBlank() && !coordinatorDefaultPassword.isBlank()) { //No hay ningún campo con valores en blanco o vacíos
                    CoordinadorDTO newCoordinador = new CoordinadorDTO(coordinatorIdentification, TipoIdentificacion.valueOf(coordinatorIdentificationType.toUpperCase()), coordinatorPhone, coordinatorName, coordinatorSurname1, coordinatorSurname2, coordinatorEmail, Hasher.getSHA512(coordinatorDefaultPassword), false);
                    System.out.println("COORD EN SERVLET --> " + newCoordinador);
                    
                    
                    if(coordinatorServiceManager.addCoordinador(newCoordinador, session) == 0){
                        String subject = "EasyMobility: usuario dado de alta con éxito.";
                        String content = "¡Hola! Te informamos de que tu usuario se ha dado de alta correctamente en el sistema EasyMobility.\n La contraseña que se te ha asignado por defecto es: " + coordinatorDefaultPassword;
                        content += "\nNota: por razones de seguridad, "
                                + "deberías de cambiar tu contraseña en tu próximo inicio de sesión.";

                        String message = "";

                        try {
                            EmailUtility emailUtility = new EmailUtility();
                            emailUtility.sendEmail(coordinatorEmail, subject, content);
                            _response.sendRedirect(_response.encodeRedirectURL("gestionCoordinadores.jsp"));
                        } catch (IOException | MessagingException ex) {
                            session.setAttribute("errorEmail", ErrorMessages.ERROR_SENDING_EMAIL.toString());
                            _response.sendRedirect(_response.encodeRedirectURL("gestionCoordinadores.jsp"));
                        }
                    }else{
                        _response.sendRedirect(_response.encodeRedirectURL("nuevoCoordinador.jsp"));
                    }
                }else{
                    session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
                    _response.sendRedirect(_response.encodeRedirectURL("nuevoCoordinador.jsp"));
                }
            }else{
                session.setAttribute("errorCoordinador", ErrorMessages.ERROR_ALUMNO_YA_EXISTE.toString());
                _response.sendRedirect(_response.encodeRedirectURL("nuevoCoordinador.jsp"));
            }
        } else {
            session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
            _response.sendRedirect(_response.encodeRedirectURL("nuevoCoordinador.jsp"));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

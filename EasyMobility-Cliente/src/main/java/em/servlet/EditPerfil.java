/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: EditPerfil.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.dto.AlumnoDTO;
import em.common.dto.CoordinadorDTO;
import em.common.dto.UsuarioDTO;
import em.common.enums.ErrorMessages;
import em.service.CoordinatorServiceManager;
import em.service.StudentServiceManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static em.util.Hasher.getSHA512;

@WebServlet(name = "EditPerfil", urlPatterns = {"/editPerfil"})
public class EditPerfil extends HttpServlet {

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

        HttpSession session = _request.getSession(false);
        String userIdentification = "";

        if (_request.getParameter("userIdentification") != null) {
            userIdentification = _request.getParameter("userIdentification");
        }
        UsuarioDTO usuario;

        StudentServiceManager studentServiceManager = StudentServiceManager.getInstance();
        CoordinatorServiceManager coordinatorServiceManager = CoordinatorServiceManager.getInstance();
        if (studentServiceManager.getAlumno(userIdentification, session).getNumIdentificacion() == null
                ? userIdentification != null : !studentServiceManager.getAlumno(userIdentification, session).getNumIdentificacion().equals(userIdentification)) {
            usuario = (CoordinadorDTO) coordinatorServiceManager.getCoordinador(userIdentification, session);
        } else {
            usuario = (AlumnoDTO) studentServiceManager.getAlumno(userIdentification, session);
        }

        String oldPassword = _request.getParameter("inputPasswordOld");
        String newPassword1 = _request.getParameter("inputPasswordNew");
        String newPassword2 = _request.getParameter("inputPasswordNewVerify");

        if (!oldPassword.isBlank() && !newPassword1.isBlank() && !newPassword2.isBlank()) {
            if (getSHA512(oldPassword) == null ? usuario.getHashContrasenya() == null : getSHA512(oldPassword).equals(usuario.getHashContrasenya())) {
                if (newPassword1 == null ? newPassword2 == null : newPassword1.equals(newPassword2)) {
                    String hashPwd = getSHA512(newPassword1);
                    usuario.setHashContrasenya(hashPwd);
                    usuario.setHaCambiadoPwd(true);
                } else {
                    session.setAttribute("errorEditar", ErrorMessages.ERROR_PASSWORDS_DIFERENTES.toString());
                }
            } else {
                session.setAttribute("errorEditar", ErrorMessages.ERROR_PASSWORD_ORIGINAL_INCORRECTA.toString());
            }

            if (usuario instanceof CoordinadorDTO) {
                coordinatorServiceManager.editCoordinador((CoordinadorDTO) usuario, session);
            } else {
                studentServiceManager.editAlumno((AlumnoDTO) usuario, session);
            }
            _response.sendRedirect(_response.encodeRedirectURL("editarPerfil.jsp"));
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

/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Login.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.dto.AdministradorDTO;
import em.common.enums.ErrorMessages;
import em.service.AdministratorServiceManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static em.util.Hasher.getSHA512;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

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

        String userIdentification = _request.getParameter("userIdentification");
        String userPwd = _request.getParameter("userPassword");
        String hashPwd = getSHA512(userPwd);

        if (!userIdentification.isBlank() && !userPwd.isBlank()) {

            AdministratorServiceManager administratorServiceManager = AdministratorServiceManager.getInstance();
            AdministradorDTO administradorRetrieved = administratorServiceManager.getAdministrador(userIdentification, session);

            if (administradorRetrieved.getNumIdentificacion().length() > 0) {  // Verifica que el administrador devuelto existe y no es un objeto vacío
                if (hashPwd.equals(administradorRetrieved.getHashContrasenya())) { // Verifica que la contraseña es la correcta
                    session.setAttribute("admin", administradorRetrieved);
                    session.setAttribute("userId", userIdentification);
                    session.setMaxInactiveInterval(30 * 60);
                    _response.sendRedirect(_response.encodeRedirectURL("gestionCoordinadores.jsp"));
                } else {
                    session.setAttribute("errorAdministrator", ErrorMessages.ERROR_CONTRASENYA);
                    _response.sendRedirect(_response.encodeRedirectURL("login.jsp"));
                }
            } else {
                session.setAttribute("errorAdministrator", ErrorMessages.ERROR_DATOS_INTRODUCIDOS.toString());
                _response.sendRedirect(_response.encodeRedirectURL("login.jsp"));
            }

        } else {
            session.setAttribute("errorAdministrator", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
            _response.sendRedirect(_response.encodeRedirectURL("login.jsp"));
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

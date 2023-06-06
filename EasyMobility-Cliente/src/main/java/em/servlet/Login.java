/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Login.java
    Date: 5 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.servlet;

import em.common.dto.AlumnoDTO;
import em.common.dto.CoordinadorDTO;
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

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    private static final String USUARIO_ALUMNO = "userAlumno";
    private static final String USUARIO_COORDINADOR = "userCoordinator";

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

        HttpSession session = _request.getSession(true);

        String userType = _request.getParameter("userType");
        String userIdentification = _request.getParameter("userIdentification");
        String userPwd = _request.getParameter("userPassword");
        String hashPwd = getSHA512(userPwd);
        
        if (!userIdentification.isBlank() && !userPwd.isBlank()) {
            if (userType.equals(USUARIO_ALUMNO)) {
                StudentServiceManager studentServiceManager = StudentServiceManager.getInstance();
                AlumnoDTO alumnoRetrieved = studentServiceManager.getAlumno(userIdentification, session);

                if (alumnoRetrieved.getNumIdentificacion().length() > 0) {  // Verifica que el alumno devuelto existe y no es un objeto vacío
                    if (hashPwd.equals(alumnoRetrieved.getHashContrasenya())) { // Verifica que la contraseña es la correcta
                        session.setAttribute("userId", alumnoRetrieved.getNumIdentificacion());
                        session.setAttribute("alumno", alumnoRetrieved);
                        session.setMaxInactiveInterval(30 * 60);
                        
                        if(! alumnoRetrieved.isHaCambiadoPwd()){
                            session.setAttribute("warning", ErrorMessages.WARNING_CAMBIO_PWD);
                        }
                        _response.sendRedirect(_response.encodeRedirectURL("homeAlumno.jsp"));
                    } else {
                        session.setAttribute("errorAlumno", ErrorMessages.ERROR_CONTRASENYA);
                        _response.sendRedirect(_response.encodeRedirectURL("login.jsp"));
                        
                    }
                } else {
                    session.setAttribute("errorAlumno", ErrorMessages.ERROR_DATOS_INTRODUCIDOS.toString());
                    _response.sendRedirect(_response.encodeRedirectURL("login.jsp"));
                }
            } else if (userType.equals(USUARIO_COORDINADOR)) {
                CoordinatorServiceManager coordinatorServiceManager = CoordinatorServiceManager.getInstance();
                CoordinadorDTO coordinadorRetrived = coordinatorServiceManager.getCoordinador(userIdentification, session);

                if (coordinadorRetrived.getNumIdentificacion().length() > 0) {  // Verifica que el coordinador devuelto existe y no es un objeto vacío
                    if (hashPwd.equals(coordinadorRetrived.getHashContrasenya())) { // Verifica que la contraseña es la correcta
                        session.setAttribute("userId", coordinadorRetrived.getNumIdentificacion());
                        session.setAttribute("coordinator", coordinadorRetrived);
                        session.setMaxInactiveInterval(30 * 60);
                        
                        if(! coordinadorRetrived.isHaCambiadoPwd()){
                            session.setAttribute("warning", ErrorMessages.WARNING_CAMBIO_PWD);
                        }
                        _response.sendRedirect(_response.encodeRedirectURL("homeCoordinador.jsp"));
                    } else {
                        session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CONTRASENYA);
                        _response.sendRedirect(_response.encodeRedirectURL("login.jsp"));
                    }
                } else {
                    session.setAttribute("errorCoordinador", ErrorMessages.ERROR_DATOS_INTRODUCIDOS.toString());
                    _response.sendRedirect(_response.encodeRedirectURL("login.jsp"));
                }
            }
        } else {
        session.setAttribute("errorLogin", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
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

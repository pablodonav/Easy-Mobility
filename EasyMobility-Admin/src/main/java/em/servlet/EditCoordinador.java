/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: EditCoordinador.java
    Date: 5 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.servlet;

import em.common.dto.CoordinadorDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.service.CoordinatorServiceManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EditCoordinador", urlPatterns = {"/editCoordinador"})
public class EditCoordinador extends HttpServlet {

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
            CoordinadorDTO coordinadorEditar= coordinatorServiceManager.getCoordinador(coordinatorIdentification, session);
            if (coordinadorEditar.getNumIdentificacion().length() > 0) { //Comprueba que el coordinador existe y se ha obtenido correctamente
                //Datos del coordinador a editar en el sistema
                String coordinatorName = _request.getParameter("coordinatorName");
                String coordinatorSurname1 = _request.getParameter("coordinatorSurname1");
                String coordinatorSurname2 = _request.getParameter("coordinatorSurname2");
                String coordinatorEmail = _request.getParameter("coordinatorEmail");
                Integer coordinatorPhone = Integer.valueOf(_request.getParameter("phone"));
                
                // Para coordinadores que no tienen dos apellidos
                if(coordinatorSurname2 == null){
                    coordinatorSurname2 = "";
                }

                if (!coordinatorName.isBlank()
                        && !coordinatorSurname1.isBlank() 
                        && !coordinatorEmail.isBlank()) { //No hay ningún campo con valores en blanco o vacíos
                    //Editar información personal de coordinador
                    coordinadorEditar.setNombre(coordinatorName);
                    coordinadorEditar.setApellido1(coordinatorSurname1);
                    coordinadorEditar.setApellido2(coordinatorSurname2);
                    coordinadorEditar.setCorreo(coordinatorEmail);
                    coordinadorEditar.setTelefono(coordinatorPhone);
                    
                    if (coordinatorServiceManager.editCoordinador(coordinadorEditar, session) == 0) { //Verifica que el coordinador se ha modificado con éxito.
                        session.setAttribute("exito", SuccessMessages.EXITO_EDIT_COORDINADOR.toString());
                        _response.sendRedirect(_response.encodeRedirectURL("gestionCoordinadores.jsp"));
                    } else {
                        session.setAttribute("errorCoordinador", ErrorMessages.ERROR_EDIT_COORDINADOR.toString());
                        _response.sendRedirect(_response.encodeRedirectURL("editarCoordinador.jsp"));
                    }
                } else {
                    session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
                    _response.sendRedirect(_response.encodeRedirectURL("editarCoordinador.jsp"));
                }
            } else {
                session.setAttribute("errorCoordinador", ErrorMessages.ERROR_OBTENER_COORDINADOR.toString());
                _response.sendRedirect(_response.encodeRedirectURL("editarCoordinador.jsp"));
            }
        } else {
            session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
            _response.sendRedirect(_response.encodeRedirectURL("editarCoordinador.jsp"));
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

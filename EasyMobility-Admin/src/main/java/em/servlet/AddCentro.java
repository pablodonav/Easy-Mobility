/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AddCentro.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.dto.CentroDTO;
import em.common.dto.LocalizacionDTO;
import em.common.dto.UniversidadDTO;
import em.common.enums.ErrorMessages;
import em.service.CenterServiceManager;
import em.service.UniversityServiceManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddCentro", urlPatterns = {"/addCentro"})
public class AddCentro extends HttpServlet {

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
        
        String nombre = _request.getParameter("nombreCentro");
        String pais = _request.getParameter("paisCentro");
        String ciudad = _request.getParameter("ciudadCentro");
        String direccion = _request.getParameter("direcCentro");
        String universidadId = _request.getParameter("universidadId");
        HttpSession session = _request.getSession();
        
        if ((!nombre.isBlank()) && (!pais.isBlank()) && (!ciudad.isBlank()) && (!direccion.isBlank())){
            UniversityServiceManager universityServiceManager = UniversityServiceManager.getInstance();
            UniversidadDTO universidad = universityServiceManager.getUniversidad(Long.valueOf(universidadId), session);
            CenterServiceManager centerServiceManager = CenterServiceManager.getInstance();
            CentroDTO newCentro = new CentroDTO(null, nombre, universidad, new LocalizacionDTO(null, ciudad, pais, direccion));
            centerServiceManager.addCentro(newCentro, session);
        }else{
            session.setAttribute("errorAreaEstudios", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
        }
        
        //Redireccionamiento realizado con javascript al JSP nuevoAlumno.jsp
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

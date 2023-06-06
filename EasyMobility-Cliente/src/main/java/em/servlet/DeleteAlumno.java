/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: DeleteAlumno.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.dto.AcuerdoEstudiosDTO;
import em.common.dto.FicheroDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.service.FileServiceManager;
import em.service.StudyAgreementsServiceManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeleteAlumno", urlPatterns = {"/deleteAlumno"})
public class DeleteAlumno extends HttpServlet {

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

        String numIdAlumnoRemove = _request.getParameter("numIdentificacionAlumnoBorrar");
        HttpSession session = _request.getSession(false);

        System.out.println("ALUMNO A BORRAR --> " + numIdAlumnoRemove);

        if (!numIdAlumnoRemove.isBlank()) {
            FileServiceManager fileServiceManager = FileServiceManager.getInstance();
            List<FicheroDTO> studentFiles = fileServiceManager.getFicheroByAlumn(numIdAlumnoRemove, session);

            if (studentFiles != null) {
                for (FicheroDTO ficheroABorrar : studentFiles) {
                    if (fileServiceManager.deleteFile(ficheroABorrar.getId(), session) != 0) {
                        _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                        return; // Detener ejecución del servlet si el borrado ha fallado
                    }
                }
            }

            StudyAgreementsServiceManager studyAgreementsServiceManager = StudyAgreementsServiceManager.getInstance();
            AcuerdoEstudiosDTO acuerdoAlumnoAEliminar = studyAgreementsServiceManager.getAcuerdoByStudent(numIdAlumnoRemove, session);

            if (acuerdoAlumnoAEliminar.getId() > 0) { //El acuerdo devuelto existe en la base de datos
                if (studyAgreementsServiceManager.deleteAcuerdo(String.valueOf(acuerdoAlumnoAEliminar.getId()), session) == 0) { //Comprueba que el acuerdo se ha borrado con éxito
                    session.setAttribute("exito", SuccessMessages.EXITO_DELETE_ALUMNO.toString()); // Alumno se elimina automáticamente al eliminar acuerdo
                }
            }
        } else {
            session.setAttribute("errorAlumno", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
        }

        _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
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

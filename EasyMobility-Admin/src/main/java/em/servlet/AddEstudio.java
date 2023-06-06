/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AddEstudio.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.common.dto.AreaEstudiosDTO;
import em.common.dto.CentroDTO;
import em.common.dto.CoordinadorDTO;
import em.common.dto.EstudioDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.service.CenterServiceManager;
import em.service.CoordinatorServiceManager;
import em.service.StudyAreaServiceManager;
import em.service.StudyServiceManager;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddEstudio", urlPatterns = {"/addEstudio"})
public class AddEstudio extends HttpServlet {

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

        CentroDTO centroEstudio = null;
        AreaEstudiosDTO areaEstudio = null;

        // Obtener los parámetros del formulario
        String nombreEstudio = _request.getParameter("studyName");
        String idAreaEstudios = _request.getParameter("areaEstudiosId");
        String idCoordinador = _request.getParameter("coordinadorId");
        String idUniversidad = _request.getParameter("university-select");
        String idCentro = _request.getParameter("center-select");

        if (nombreEstudio.isBlank()) {
            session.setAttribute("nombreIncorrecto", ErrorMessages.ERROR_NOMBRE_NULO_INCORRECTO.toString());
            _response.sendRedirect(_response.encodeRedirectURL("nuevoEstudio.jsp"));
        } else if (idAreaEstudios == null) {
            session.setAttribute("areaEstudioIncorrecto", ErrorMessages.ERROR_AREA_ESTUDIO_NO_SELECCIONADA.toString());
            _response.sendRedirect(_response.encodeRedirectURL("nuevoEstudio.jsp"));
        } else if (idCoordinador == null) {
            session.setAttribute("coordinadorIncorrecto", ErrorMessages.ERROR_COORDINADOR_NO_SELECCIONADO.toString());
            _response.sendRedirect(_response.encodeRedirectURL("nuevoEstudio.jsp"));
        } else if (idUniversidad == null) {
            session.setAttribute("universidadIncorrecta", ErrorMessages.ERROR_UNIVERSIDAD_NO_SELECCIONADA.toString());
            _response.sendRedirect(_response.encodeRedirectURL("nuevoEstudio.jsp"));
        } else if (idCentro == null) {
            session.setAttribute("centroIncorrecto", ErrorMessages.ERROR_CENTRO_NO_SELECCIONADO.toString());
            _response.sendRedirect(_response.encodeRedirectURL("nuevoEstudio.jsp"));
        } else {
            CenterServiceManager centerServiceManager = CenterServiceManager.getInstance();
            CentroDTO[] centros = centerServiceManager.getCentros(session);
            for (CentroDTO centro : centros) {
                if ((Objects.equals(centro.getId().getIdCentro(), Long.valueOf(idCentro)))
                        && (Objects.equals(centro.getId().getIdUniversidad(), Long.valueOf(idUniversidad)))) {
                    centroEstudio = centro;
                }
            }

            StudyAreaServiceManager studyAreaServiceManager = StudyAreaServiceManager.getInstance();
            AreaEstudiosDTO[] areas = studyAreaServiceManager.getAreasEstudio(session);
            for (AreaEstudiosDTO area : areas) {
                if (Objects.equals(area.getId(), Long.valueOf(idAreaEstudios))) {
                    areaEstudio = area;
                }
            }

            CoordinatorServiceManager coordinatorServiceManager = CoordinatorServiceManager.getInstance();
            CoordinadorDTO coordinadorEstudio = coordinatorServiceManager.getCoordinador(idCoordinador, session);

            if ((centroEstudio != null) && (areaEstudio != null) && (coordinadorEstudio != null)) {

                EstudioDTO newEstudio = new EstudioDTO(null, centroEstudio, nombreEstudio, areaEstudio, coordinadorEstudio);

                StudyServiceManager studyServiceManager = StudyServiceManager.getInstance();
                studyServiceManager.addEstudio(newEstudio, session);

                session.setAttribute("exitoEstudio", SuccessMessages.EXITO_ADD_ESTUDIO.toString());
                _response.sendRedirect(_response.encodeRedirectURL("gestionEstudios.jsp"));
            }
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

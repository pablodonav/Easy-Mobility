/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: EditEstudio.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.servlet;

import em.bd.compositekeys.CompositeKeyEstudio;
import em.common.dto.AreaEstudiosDTO;
import em.common.dto.CentroDTO;
import em.common.dto.CoordinadorDTO;
import em.common.dto.EstudioDTO;
import em.common.enums.ErrorMessages;
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

@WebServlet(name = "EditEstudio", urlPatterns = {"/editEstudio"})
public class EditEstudio extends HttpServlet {

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
        EstudioDTO estudio;

        // Obtener los parámetros del formulario
        String idEstudio = _request.getParameter("idEstudio");
        String idAntiguoCentro = _request.getParameter("idAntiguoCentro");
        String idAntiguaUniversidad = _request.getParameter("idAntiguaUniversidad");
        String nombreNuevoEstudio = _request.getParameter("studyName");
        String idNuevaAreaEstudios = _request.getParameter("areaEstudiosId");
        String idNuevoCoordinador = _request.getParameter("coordinadorId");
        String idNuevaUniversidad = _request.getParameter("university-select");
        String idNuevoCentro = _request.getParameter("center-select");

        session.setAttribute("fromServlet", "true");
        session.setAttribute("idEstudio", idEstudio);
        session.setAttribute("idCentro", idAntiguoCentro);
        session.setAttribute("idUniversidad", idAntiguaUniversidad);
        session.setAttribute("idAreaEstudio", idNuevaAreaEstudios);
        session.setAttribute("numIdentificacion", idNuevoCoordinador);
        
        if (nombreNuevoEstudio.isBlank()) {
            session.setAttribute("nombreIncorrecto", ErrorMessages.ERROR_NOMBRE_NULO_INCORRECTO.toString());
            _response.sendRedirect(_response.encodeRedirectURL("editarEstudio.jsp"));
        } else if (idNuevaUniversidad == null) {
            session.setAttribute("universidadIncorrecta", ErrorMessages.ERROR_UNIVERSIDAD_NO_SELECCIONADA.toString());
            _response.sendRedirect(_response.encodeRedirectURL("editarEstudio.jsp"));
        } else if (idNuevoCentro == null) {
            session.setAttribute("centroIncorrecto", ErrorMessages.ERROR_CENTRO_NO_SELECCIONADO.toString());
            _response.sendRedirect(_response.encodeRedirectURL("editarEstudio.jsp"));
        } else {
            CenterServiceManager centerServiceManager = CenterServiceManager.getInstance();
            CentroDTO[] centros = centerServiceManager.getCentros(session);
            for (CentroDTO centro : centros) {
                if ((Objects.equals(centro.getId().getIdCentro(), Long.valueOf(idNuevoCentro)))
                        && (Objects.equals(centro.getId().getIdUniversidad(), Long.valueOf(idNuevaUniversidad)))) {
                    centroEstudio = centro;
                }
            }

            StudyAreaServiceManager studyAreaServiceManager = StudyAreaServiceManager.getInstance();
            AreaEstudiosDTO[] areas = studyAreaServiceManager.getAreasEstudio(session);
            for (AreaEstudiosDTO area : areas) {
                if (Objects.equals(area.getId(), Long.valueOf(idNuevaAreaEstudios))) {
                    areaEstudio = area;
                }
            }

            if(idNuevoCoordinador != null) {
                CoordinatorServiceManager coordinatorServiceManager = CoordinatorServiceManager.getInstance();
                CoordinadorDTO coordinadorEstudio = coordinatorServiceManager.getCoordinador(idNuevoCoordinador, session);
            }

            if ((centroEstudio != null) && (areaEstudio != null)) {

                StudyServiceManager studyServiceManager = StudyServiceManager.getInstance();
                estudio = studyServiceManager.getEstudio(idAntiguoCentro, idAntiguaUniversidad, idEstudio, session);
                        
                if(estudio != null) {
                    estudio.setAreaEstudios(areaEstudio);
                    CompositeKeyEstudio cke = estudio.getId();
                    cke.setIdCentro(centroEstudio.getId());
                    estudio.setId(cke);
                    estudio.setCentro(centroEstudio);
                    estudio.setNombreEstudio(nombreNuevoEstudio);
                    studyServiceManager.editEstudio(estudio, session);
                }
                
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

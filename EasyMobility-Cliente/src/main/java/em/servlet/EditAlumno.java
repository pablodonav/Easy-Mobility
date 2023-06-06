/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: EditAlumno.java
    Date: 5 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.servlet;

import em.common.dto.AcuerdoEstudiosDTO;
import em.common.dto.AlumnoDTO;
import em.common.dto.EstudioDTO;
import em.common.dto.IdiomaDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.PeriodoIntercambio;
import em.service.LanguageServiceManager;
import em.service.StudentServiceManager;
import em.service.StudyAgreementsServiceManager;
import em.service.StudyServiceManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EditAlumno", urlPatterns = {"/editAlumno"})
public class EditAlumno extends HttpServlet {

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

        String studentIdentification = _request.getParameter("studentIdentification");

        if (!studentIdentification.isBlank()) {
            StudentServiceManager studentServiceManager = StudentServiceManager.getInstance();
            AlumnoDTO alumnoEditar = studentServiceManager.getAlumno(studentIdentification, session);

            if (alumnoEditar.getNumIdentificacion().length() > 0) { //Comprueba que el alumno existe y se ha obtenido correctamente

                String studentName = _request.getParameter("studentName");
                String studentSurname1 = _request.getParameter("studentSurname1");
                String studentSurname2 = _request.getParameter("studentSurname2");
                String studentEmail = _request.getParameter("studentEmail");
                Integer studentPhone = Integer.valueOf(_request.getParameter("phone"));

                String universityId = _request.getParameter("universityId");
                String centerId = _request.getParameter("centerId");
                String studyId = _request.getParameter("studyId");
                String languageId = _request.getParameter("languageId");
                String academicYear = _request.getParameter("academicYear");
                String exchangePeriod = _request.getParameter("exchangePeriod");
                
                if (!studentName.isBlank() && !studentSurname1.isBlank() && !studentSurname2.isBlank()
                        && !studentEmail.isBlank() && studentPhone > 0 && !universityId.isBlank()
                        && !centerId.isBlank() && !studyId.isBlank() && !languageId.isBlank()
                        && !academicYear.isBlank() && !exchangePeriod.isBlank()) {
                    //Editar información personal de alumno
                    alumnoEditar.setNombre(studentName);
                    alumnoEditar.setApellido1(studentSurname1);
                    alumnoEditar.setApellido2(studentSurname2);
                    alumnoEditar.setCorreo(studentEmail);
                    alumnoEditar.setTelefono(studentPhone);
                    studentServiceManager.editAlumno(alumnoEditar, session);
                    
                    if(studentServiceManager.editAlumno(alumnoEditar, session) == 0){ // Comprueba que el alumno se ha editado con éxito
                       //Editar información del acuerdo existente para el alumno
                       StudyAgreementsServiceManager studyAgreementsServiceManager = StudyAgreementsServiceManager.getInstance();
                       AcuerdoEstudiosDTO acuerdoEditar = studyAgreementsServiceManager.getAcuerdoByStudent(studentIdentification, session);

                       if (acuerdoEditar.getId() > 0) { //Comprueba que el acuerdo existe y se ha obtenido correctamente
                           StudyServiceManager studyServiceManager = StudyServiceManager.getInstance();
                           EstudioDTO estudioOut = studyServiceManager.getEstudio(centerId, universityId, studyId, session);
                           System.out.println("ALUMNO EDITADO ==> ");
                           
                           if (estudioOut.getNombreEstudio().length() > 0) { //Comprueba que el estudio existe y se ha obtenido correctamente
                               LanguageServiceManager languageServiceManager = LanguageServiceManager.getInstance();
                               IdiomaDTO language = languageServiceManager.getIdioma(languageId, session);
                               
                               if (language.getNombre().length() > 0) { //Comprueba que el idioma existe y se ha obtenido correctamente
                                   acuerdoEditar.setEstudioDestino(estudioOut);
                                   acuerdoEditar.setIdioma(language);
                                   acuerdoEditar.setCursoAcademico(academicYear);
                                   acuerdoEditar.setPeriodoIntercambio(PeriodoIntercambio.valueOf(exchangePeriod.toUpperCase()));
                                   
                                   System.out.println("ALUMNO EDITADO ==> " + acuerdoEditar.toJson());

                                   studyAgreementsServiceManager.editAcuerdo(acuerdoEditar, String.valueOf(acuerdoEditar.getId()), session);
                                   _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                               } else {
                                   _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                               }
                           } else {
                               _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                           }
                       } else {
                           _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                       }   
                    }else{
                        _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                    }
                }else{
                    session.setAttribute("errorAlumno", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
                    _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                }
            }else{
                _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
            }
        }else{
            session.setAttribute("errorAlumno", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
            _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
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

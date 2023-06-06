/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AddAlumno.java
    Date: 5 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.servlet;

import em.common.dto.AcuerdoEstudiosDTO;
import em.common.dto.AlumnoDTO;
import em.common.dto.CoordinadorDTO;
import em.common.dto.EstudioDTO;
import em.common.dto.IdiomaDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.PeriodoIntercambio;
import em.common.enums.SuccessMessages;
import em.common.enums.TipoIdentificacion;
import em.service.LanguageServiceManager;
import em.service.StudentServiceManager;
import em.service.StudyAgreementsServiceManager;
import em.service.StudyServiceManager;
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

/**
 *
 * @author Ususario
 */
@WebServlet(name = "AddAlumno", urlPatterns = {"/addAlumno"})
public class AddAlumno extends HttpServlet {

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
            AlumnoDTO alumnoRetrived = studentServiceManager.getAlumno(studentIdentification, session);

            if (alumnoRetrived.getNumIdentificacion().length() == 0) { //Comprueba que no existe un alumno con el mismo número de identificación
                session.setAttribute("errorAlumno", null); //Resetear variable
                //Coordinador que realiza la operación
                CoordinadorDTO coordinator = (CoordinadorDTO) session.getAttribute("coordinator");
                //Datos del nuevo alumno a insertar en el sistema
                String studentIdentificationType = _request.getParameter("identificationType");
                String studentName = _request.getParameter("studentName");
                String studentSurname1 = _request.getParameter("studentSurname1");
                String studentSurname2 = _request.getParameter("studentSurname2");
                String studentEmail = _request.getParameter("studentEmail");
                String studentDefaultPassword = _request.getParameter("studentDefaultPassword");
                Integer studentPhone = Integer.valueOf(_request.getParameter("phone"));

                if (!studentName.isBlank() && !studentSurname2.isBlank()
                        && !studentSurname1.isBlank() && !studentEmail.isBlank() && !studentDefaultPassword.isBlank()) { //No hay ningún campo con valores en blanco o vacíos
                    AlumnoDTO newAlumno = new AlumnoDTO(studentIdentification, TipoIdentificacion.valueOf(studentIdentificationType.toUpperCase()),
                            studentPhone, studentName, studentSurname1, studentSurname2, studentEmail, Hasher.getSHA512(studentDefaultPassword), false);

                    newAlumno.setHaCambiadoPwd(false);
                    
                    //Obtener Estudio In a partir del estudio dirigo por el coordinador que realiza la operación
                    StudyServiceManager studyServiceManager = StudyServiceManager.getInstance();
                    EstudioDTO estudioIn = studyServiceManager.getEstudioByCoordinator(coordinator.getNumIdentificacion(), session);

                    if (estudioIn.getNombreEstudio().length() > 0) { //Verifica que se ha obtenido el estudio de origen
                        //Obtener Estudio Out a partir de los datos seleccionados en el formulario
                        String universityId = _request.getParameter("universityId");
                        String centerId = _request.getParameter("centerId");
                        String studyId = _request.getParameter("studyId");
                        EstudioDTO estudioOut = studyServiceManager.getEstudio(centerId, universityId, studyId, session);

                        if (estudioOut.getNombreEstudio().length() > 0) {//Verifica que se ha obtenido el estudio de destino

                            String languageId = _request.getParameter("languageId");
                            LanguageServiceManager languageServiceManager = LanguageServiceManager.getInstance();
                            IdiomaDTO language = languageServiceManager.getIdioma(languageId, session);

                            if (language.getNombre().length() > 0) {//Verifica que se ha obtenido el idioma seleccionado
                                String academicYear = _request.getParameter("academicYear");
                                String exchangePeriod = _request.getParameter("exchangePeriod");

                                if (!academicYear.isBlank() && !exchangePeriod.isBlank()) {
                                    AcuerdoEstudiosDTO acuerdoAlumno = new AcuerdoEstudiosDTO(null, estudioIn, estudioOut, academicYear, PeriodoIntercambio.valueOf(exchangePeriod.toUpperCase()), newAlumno, language);
                                    StudyAgreementsServiceManager studyAgreementsServiceManager = StudyAgreementsServiceManager.getInstance();

                                    if (studyAgreementsServiceManager.addAcuerdo(acuerdoAlumno, session) == 0) { //Verifica que el alumno y acuerdo han sido añadidos con éxito.
                                        session.setAttribute("exito", SuccessMessages.EXITO_ADD_ALUMNO.toString()); // Alumno se añade automáticamente al añadir acuerdo

                                        String subject = "EasyMobility: usuario dado de alta con éxito.";
                                        String content = "¡Hola! Te informamos de que tu usuario se ha dado de alta correctamente en el sistema EasyMobility.\n La contraseña que se te ha asignado por defecto es: " + studentDefaultPassword;
                                        content += "\nNota: por razones de seguridad, "
                                                + "deberías de cambiar tu contraseña en tu próximo inicio de sesión.";

                                        String message = "";

                                        try {
                                            EmailUtility emailUtility = new EmailUtility();
                                            emailUtility.sendEmail(studentEmail, subject, content);
                                            _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                                        } catch (IOException | MessagingException ex) {
                                            session.setAttribute("errorEmail", ErrorMessages.ERROR_SENDING_EMAIL.toString());
                                            _response.sendRedirect(_response.encodeRedirectURL("gestionAlumnos.jsp"));
                                        }
                                    }
                                } else {
                                    _response.sendRedirect(_response.encodeRedirectURL("nuevoAlumno.jsp"));
                                }
                            } else {
                                _response.sendRedirect(_response.encodeRedirectURL("nuevoAlumno.jsp"));
                            }
                        } else {
                            _response.sendRedirect(_response.encodeRedirectURL("nuevoAlumno.jsp"));
                        }
                    } else {
                        _response.sendRedirect(_response.encodeRedirectURL("nuevoAlumno.jsp"));
                    }
                } else {
                    session.setAttribute("errorAlumno", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
                    _response.sendRedirect(_response.encodeRedirectURL("nuevoAlumno.jsp"));
                }
            } else {
                session.setAttribute("errorAlumno", ErrorMessages.ERROR_ALUMNO_YA_EXISTE.toString());
                _response.sendRedirect(_response.encodeRedirectURL("nuevoAlumno.jsp"));
            }
        } else {
            session.setAttribute("errorAlumno", ErrorMessages.ERROR_CAMPOS_INVALIDOS.toString());
            _response.sendRedirect(_response.encodeRedirectURL("nuevoAlumno.jsp"));
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

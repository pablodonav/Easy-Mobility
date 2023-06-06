/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: StudyAgreementsServiceManager.java
    Date: 06 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.AcuerdoEstudiosDTO;
import em.common.dto.AlumnoDTO;
import em.common.dto.EstudioDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.TipoAcuerdo;
import em.rest.client.ServerAcuerdoEstudiosRestService;
import java.util.Objects;
import javax.servlet.http.HttpSession;

public class StudyAgreementsServiceManager {

    private static StudyAgreementsServiceManager moiMeme = null; //Singleton

    /**
     * Construye un StudyAgreementsServiceManager.
     *
     */
    public StudyAgreementsServiceManager() {

    }

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return StudyAgreementsServiceManager
     */
    public static StudyAgreementsServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new StudyAgreementsServiceManager();
        }
        return moiMeme;
    }

    /**
     * Devuelve la lista de alumnos de un estudio en concreto.
     * 
     * @param _estudio
     * @param _session
     * @return 
     */
    public AlumnoDTO[] getAlumnosEstudio(EstudioDTO _estudio, HttpSession _session) {
        AlumnoDTO[] alumnos;

        try {
            ServerAcuerdoEstudiosRestService acclient = new ServerAcuerdoEstudiosRestService();

            alumnos = acclient.findAlumnosEstudio(AlumnoDTO[].class,
                    String.valueOf(_estudio.getId().getIdCentro().getIdCentro()),
                    String.valueOf(_estudio.getId().getIdCentro().getIdUniversidad()),
                    String.valueOf(_estudio.getId().getIdEstudio()));
            acclient.close();

            System.out.println("Alumnos del estudio: ");
            for (AlumnoDTO alumno : alumnos) {
                System.out.println("   - " + alumno.toString());
            }

            if (alumnos == null) {
                _session.setAttribute("errorAlumno", ErrorMessages.ERROR_OBTENER_ALUMNOS);
                alumnos = new AlumnoDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorAlumno", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            alumnos = new AlumnoDTO[0];
        }

        return alumnos;
    }

    /**
     * Devuelve el acuerdo de un estudiante con id = _numIdentificacion.
     * 
     * @param _numIdentificacion
     * @param _session
     * @return 
     */
    public AcuerdoEstudiosDTO getAcuerdoByStudent(String _numIdentificacion, HttpSession _session) {
        AcuerdoEstudiosDTO acuerdo;

        try {
            ServerAcuerdoEstudiosRestService acclient = new ServerAcuerdoEstudiosRestService();

            acuerdo = acclient.findAcuerdoAlumno(_numIdentificacion);
            acclient.close();

            if (acuerdo == null) {
                _session.setAttribute("errorAcuerdos", ErrorMessages.ERROR_OBTENER_ACUERDO_ALUMNO.toString());
                acuerdo = new AcuerdoEstudiosDTO();
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorAcuerdos", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            acuerdo = new AcuerdoEstudiosDTO();
        }

        return acuerdo;
    }

    /**
     * Devuelve la lista de alumnos asociados a un coordinador con id = _numIdentificacion.
     * 
     * @param _numIdentificacion
     * @param _session
     * @return 
     */
    public AlumnoDTO[] getAlumnosByCoordinator(String _numIdentificacion, HttpSession _session) {
        AlumnoDTO[] alumnos;

        try {
            ServerAcuerdoEstudiosRestService acclient = new ServerAcuerdoEstudiosRestService();

            alumnos = acclient.findAlumnosByCoordinador(AlumnoDTO[].class, _numIdentificacion);
            acclient.close();

            if (alumnos == null) {
                _session.setAttribute("errorAcuerdos", ErrorMessages.ERROR_OBTENER_ACUERDO_ALUMNO.toString());
                alumnos = new AlumnoDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorAcuerdos", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            alumnos = new AlumnoDTO[0];
        }

        return alumnos;
    }

    /**
     * Devuelve el tipo de acuerdo en función del alumno y del estudio al que está
     * asignado el coordinador.
     * 
     * @param _alumno
     * @param _estudioCoordinador
     * @param _session
     * @return 
     */
    public String getTipoAcuerdo(AlumnoDTO _alumno, EstudioDTO _estudioCoordinador, HttpSession _session) {
        AcuerdoEstudiosDTO acuerdoAlumno;

        try {
            ServerAcuerdoEstudiosRestService acesClient = new ServerAcuerdoEstudiosRestService();

            acuerdoAlumno = (AcuerdoEstudiosDTO) acesClient.findAcuerdoAlumno(_alumno.getNumIdentificacion());
            acesClient.close();

            if ((Objects.equals(acuerdoAlumno.getEstudioOrigen().getId().getIdEstudio(), _estudioCoordinador.getId().getIdEstudio()))
                    && (Objects.equals(acuerdoAlumno.getEstudioOrigen().getId().getIdCentro().getIdCentro(), _estudioCoordinador.getId().getIdCentro().getIdCentro()))
                    && (Objects.equals(acuerdoAlumno.getEstudioOrigen().getId().getIdCentro().getIdUniversidad(), _estudioCoordinador.getId().getIdCentro().getIdUniversidad()))) {
                return TipoAcuerdo.OUT.toString();
            } else {
                return TipoAcuerdo.IN.toString();
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAcuerdos", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            acuerdoAlumno = new AcuerdoEstudiosDTO();
        }

        return "";
    }

    /**
     * Crea un nuevo acuerdo de estudios.
     * 
     * @param _acuerdo
     * @param _session
     * @return 
     */
    public int addAcuerdo(AcuerdoEstudiosDTO _acuerdo, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerAcuerdoEstudiosRestService emclient = new ServerAcuerdoEstudiosRestService();
            System.out.println("ACUERDO: " + _acuerdo.toJson());
            statusServer = emclient.create(_acuerdo);
            emclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                System.out.println("ACUERDO ERROR => " + statusServer);
                System.out.println("ACUERDO EN JAVA --> " + _acuerdo.toJson());
                _session.setAttribute("errorAcuerdos", ErrorMessages.ERROR_ADD_ACUERDO.toString());
            } else {
                statusServer = 0;
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorAcuerdos", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Elimina el acuerdo con id = _idAcuerdo.
     * 
     * @param _idAcuerdo
     * @param _session
     * @return 
     */
    public int deleteAcuerdo(String _idAcuerdo, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerAcuerdoEstudiosRestService emclient = new ServerAcuerdoEstudiosRestService();
            statusServer = emclient.remove(_idAcuerdo);
            emclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorAcuerdo", ErrorMessages.ERROR_DELETE_ACUERDO.toString());
            } else {
                statusServer = 0;
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAcuerdo", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Modifica un acuerdo existente.
     * 
     * @param _acuerdoEditado
     * @param _idAcuerdo
     * @param _session
     * @return 
     */
    public int editAcuerdo(AcuerdoEstudiosDTO _acuerdoEditado, String _idAcuerdo, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerAcuerdoEstudiosRestService emclient = new ServerAcuerdoEstudiosRestService();
            statusServer = emclient.edit(_acuerdoEditado, _idAcuerdo);
            emclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                System.out.println(statusServer);
                _session.setAttribute("errorAcuerdo", ErrorMessages.ERROR_EDIT_ACUERDO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAcuerdo", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }
}

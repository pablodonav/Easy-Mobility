/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: StudyServiceManager.java
    Date: 06 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.bd.compositekeys.CompositeKeyEstudio;
import em.common.dto.CoordinadorDTO;
import em.common.dto.EstudioDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.rest.client.ServerEstudiosRestService;
import javax.servlet.http.HttpSession;

public class StudyServiceManager {

    private static StudyServiceManager moiMeme = null; //Singleton

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return StudyServiceManager
     */
    public static StudyServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new StudyServiceManager();
        }
        return moiMeme;
    }

    /**
     * Da de alta un nuevo estudio.
     * 
     * @param _estudio
     * @param _session
     * @return 
     */
    public int addEstudio(EstudioDTO _estudio, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerEstudiosRestService estClient = new ServerEstudiosRestService();
            statusServer = estClient.create(_estudio);
            estClient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorAddEstudio", ErrorMessages.ERROR_ADD_ESTUDIO.toString());
            } else {
                statusServer = 0;
                _session.setAttribute("exitoAddEstudio", SuccessMessages.EXITO_ADD_ESTUDIO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAddEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Obtiene el estudio de un coordinador.
     * 
     * @param _numIdentificador
     * @param _session
     * @return 
     */
    public EstudioDTO getEstudioByCoordinator(String _numIdentificador, HttpSession _session) {
        EstudioDTO estudio = new EstudioDTO();

        try {
            ServerEstudiosRestService estlient = new ServerEstudiosRestService();

            estudio = estlient.findEstudioByCoordinador(EstudioDTO.class, _numIdentificador);
            estlient.close();

            if (estudio == null) {
                _session.setAttribute("errorEstudio", ErrorMessages.ERROR_OBTENER_ESTUDIO);
                estudio = new EstudioDTO();
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            estudio = new EstudioDTO();
        }

        return estudio;
    }

    /**
     * Devuelve la lista de estudios.
     * 
     * @param _session
     * @return 
     */
    public EstudioDTO[] getEstudios(HttpSession _session) {
        EstudioDTO[] estudios = new EstudioDTO[0];

        try {
            ServerEstudiosRestService estlient = new ServerEstudiosRestService();

            estudios = estlient.findAll(EstudioDTO[].class);
            estlient.close();

            if (estudios == null) {
                _session.setAttribute("errorEstudio", ErrorMessages.ERROR_OBTENER_ESTUDIOS);
                estudios = new EstudioDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            estudios = new EstudioDTO[0];
        }

        return estudios;
    }

    /**
     * Devuelve los coordinadores que están asignados a estudios.
     * 
     * @param _session
     * @return 
     */
    public CoordinadorDTO[] getCoordinadoresConEstudio(HttpSession _session) {
        int i = 0;
        CoordinadorDTO[] coordinadores = new CoordinadorDTO[0];
        EstudioDTO[] estudios = new EstudioDTO[0];

        try {
            ServerEstudiosRestService estlient = new ServerEstudiosRestService();

            estudios = estlient.findAll(EstudioDTO[].class);
            estlient.close();

            coordinadores = new CoordinadorDTO[estudios.length];

            for (EstudioDTO estudio : estudios) {
                coordinadores[i] = estudio.getCoordinador();
                i++;
            }

            if (coordinadores == null) {
                _session.setAttribute("errorEstudio", ErrorMessages.ERROR_OBTENER_ESTUDIOS);
                estudios = new EstudioDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            estudios = new EstudioDTO[0];
        }

        return coordinadores;
    }

    /**
     * Obtiene un estudio.
     * 
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @param _session
     * @return 
     */
    public EstudioDTO getEstudio(String _idCentro, String _idUniversidad, String _idEstudio, HttpSession _session) {
        EstudioDTO estudio = new EstudioDTO();

        try {
            ServerEstudiosRestService estlient = new ServerEstudiosRestService();

            estudio = estlient.find(EstudioDTO.class, _idCentro, _idUniversidad, _idEstudio);
            estlient.close();

            if (estudio == null) {
                _session.setAttribute("errorEstudio", ErrorMessages.ERROR_OBTENER_ESTUDIO);
                estudio = new EstudioDTO();
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            estudio = new EstudioDTO();
        }

        return estudio;
    }

    /**
     * Devuelve los estudios ajenos a un coordinador.
     * 
     * @param _numIdCoordinador
     * @param _session
     * @return 
     */
    public EstudioDTO[] getEstudiosAjenosACoordinador(String _numIdCoordinador, HttpSession _session) {
        EstudioDTO[] estudiosAjenosCoordinador = new EstudioDTO[0];

        try {
            ServerEstudiosRestService estlient = new ServerEstudiosRestService();

            estudiosAjenosCoordinador = estlient.findDifferentStudies(EstudioDTO[].class, _numIdCoordinador);
            estlient.close();

            System.out.println("ID COORD --> " + _numIdCoordinador);

            if (estudiosAjenosCoordinador == null) {
                _session.setAttribute("errorEstudio", ErrorMessages.ERROR_OBTENER_ESTUDIOS);
                estudiosAjenosCoordinador = new EstudioDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            estudiosAjenosCoordinador = new EstudioDTO[0];
        }

        return estudiosAjenosCoordinador;
    }

    /**
     * Elimina un estudio.
     * 
     * @param _id
     * @param _session
     * @return 
     */
    public int deleteEstudio(CompositeKeyEstudio _id, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerEstudiosRestService esclient = new ServerEstudiosRestService();
            statusServer = esclient.remove(String.valueOf(_id.getIdCentro().getIdCentro()), String.valueOf(_id.getIdCentro().getIdUniversidad()), String.valueOf(_id.getIdEstudio()));
            esclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorDeleteEstudio", ErrorMessages.ERROR_DELETE_ESTUDIO.toString());
            } else {
                statusServer = 0;
                _session.setAttribute("exitoDeleteEstudio", SuccessMessages.EXITO_DELETE_ESTUDIO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorDeleteEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Modifica un estudio.
     * 
     * @param _estudio
     * @param _session
     * @return 
     */
    public int editEstudio(EstudioDTO _estudio, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerEstudiosRestService esclient = new ServerEstudiosRestService();
            statusServer = esclient.edit(_estudio, String.valueOf(_estudio.getId().getIdCentro().getIdCentro()), String.valueOf(_estudio.getId().getIdCentro().getIdUniversidad()), String.valueOf(_estudio.getId().getIdEstudio()));
            esclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorEditarEstudio", ErrorMessages.ERROR_EDITAR_ESTUDIO.toString());
            } else {
                System.out.println(statusServer);
                statusServer = 0;
                _session.setAttribute("exitoEditarEstudio", SuccessMessages.EXITO_EDIT_ESTUDIO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorEditarEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }
}

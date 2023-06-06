/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: StudyServiceManager.java
    Date: 06 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.service;

import em.common.dto.EstudioDTO;
import em.common.enums.ErrorMessages;
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
     * Método que devuelve el estudio al que está asignado el coordinador
     * con id = _numIdentificador.
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
     * Devuelve la lista completa de estudios.
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

            for (EstudioDTO estudio : estudios) {
                System.out.println("ESTUDIO --> " + estudio.toString());
            }

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
     * Devuelve el estudio con _idCentro, _idUniversidad y _idEstudio.
     * 
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @param _session
     * @return 
     */
    public EstudioDTO getEstudio(String _idCentro, String _idUniversidad, String _idEstudio, 
            HttpSession _session) {
        EstudioDTO estudio = new EstudioDTO();

        try {
            ServerEstudiosRestService estlient = new ServerEstudiosRestService();

            estudio = estlient.find(EstudioDTO.class, _idCentro, _idUniversidad, _idEstudio);
            estlient.close();

            System.out.println("ESTUDIO ENCONTRADO ==> " + estudio.toJson());
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
     * Devuelve la lista de estudios que son ajenos al coordinador con
     * id = _numIdCoordinador.
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
}

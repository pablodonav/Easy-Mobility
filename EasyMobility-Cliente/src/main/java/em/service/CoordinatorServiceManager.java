/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CoordinatorServiceManager.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.CoordinadorDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.rest.client.ServerCoordinadorRestService;
import javax.servlet.http.HttpSession;

public class CoordinatorServiceManager {

    private static CoordinatorServiceManager moiMeme = null; //Singleton

    /**
     * Construye un CoordinatorServiceManager.
     *
     */
    public CoordinatorServiceManager() {

    }

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return UserServiceManager
     */
    public static CoordinatorServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new CoordinatorServiceManager();
        }
        return moiMeme;
    }

    /**
     * Devuelve todos los coordinadores dados de alta.
     * 
     * @param _session
     * @return 
     */
    public CoordinadorDTO[] getCoordinadores(HttpSession _session) {
        CoordinadorDTO[] coordinadores = new CoordinadorDTO[0];

        try {
            ServerCoordinadorRestService emclient = new ServerCoordinadorRestService();

            coordinadores = emclient.findAll(CoordinadorDTO[].class);
            emclient.close();

            for (CoordinadorDTO coordinador : coordinadores) {
                System.out.println("COORDINADOR --> " + coordinador.toString());
            }

            if (coordinadores == null) {
                _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_OBTENER_COORDINADORES);
                coordinadores = new CoordinadorDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            coordinadores = new CoordinadorDTO[0];
        }

        return coordinadores;
    }

    /**
     * Modifica un coordinador existente.
     * 
     * @param _coordinador
     * @param _session
     * @return 
     */
    public int editCoordinador(CoordinadorDTO _coordinador, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerCoordinadorRestService coorclient = new ServerCoordinadorRestService();
            statusServer = coorclient.edit(_coordinador, _coordinador.getNumIdentificacion());
            coorclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                System.out.println(statusServer);
                System.out.println("COORDINADOR --> " + _coordinador.toJson());
                _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_EDIT_COORDINADOR.toString());
            } else {
                System.out.println(statusServer);
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_EDIT_PASSWORD.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Devuelve un coordinador con numIdentificacion = _numIdentificacion.
     * 
     * @param _numIdentificacion
     * @param _session
     * @return 
     */
    public CoordinadorDTO getCoordinador(String _numIdentificacion, HttpSession _session) {
        CoordinadorDTO coordinador = new CoordinadorDTO();

        try {
            ServerCoordinadorRestService emclient = new ServerCoordinadorRestService();

            coordinador = emclient.find(CoordinadorDTO.class, _numIdentificacion);
            emclient.close();

            System.out.println("COORDINADOR LOGIN ==> " + coordinador);

            if (coordinador == null) {
                _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_OBTENER_COORDINADOR);
                coordinador = new CoordinadorDTO();
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            coordinador = new CoordinadorDTO();
        }

        return coordinador;
    }

    /**
     * Devuelve el coordinador con correo = _email.
     * 
     * @param _email
     * @param _session
     * @return 
     */
    public CoordinadorDTO getCoordinadorByEmail(String _email, HttpSession _session) {
        CoordinadorDTO coordinador = new CoordinadorDTO();

        ServerCoordinadorRestService emclient = new ServerCoordinadorRestService();

        coordinador = emclient.findByEmail(CoordinadorDTO.class, _email);
        emclient.close();

        return coordinador;
    }
}

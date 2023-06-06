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
import java.util.Arrays;
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
     * Devuelve la lista de coordinadores.
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
     * Devuelve coordinadores que no tengan estudios.
     *
     * @param _session
     * @return
     */
    public CoordinadorDTO[] getCoordinadoresSinEstudios(HttpSession _session) {
        CoordinadorDTO[] coordinadoresConEstudios = new CoordinadorDTO[0];
        CoordinadorDTO[] coordinadoresSinEstudios = new CoordinadorDTO[0];
        CoordinadorDTO[] coordinadores = new CoordinadorDTO[0];

        try {
            ServerCoordinadorRestService emclient = new ServerCoordinadorRestService();
            coordinadores = emclient.findAll(CoordinadorDTO[].class);
            emclient.close();

            StudyServiceManager studyServiceManager = StudyServiceManager.getInstance();
            coordinadoresConEstudios = studyServiceManager.getCoordinadoresConEstudio(_session);

            for (CoordinadorDTO coordinador : coordinadores) {
                boolean encontrado = false;
                for (CoordinadorDTO otroCoordinador : coordinadoresConEstudios) {
                    if (coordinador.equals(otroCoordinador)) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    // El coordinador no está en el otro vector
                    // Agregamos el coordinador al resultado
                    coordinadoresSinEstudios = Arrays.copyOf(coordinadoresSinEstudios, coordinadoresSinEstudios.length + 1);
                    coordinadoresSinEstudios[coordinadoresSinEstudios.length - 1] = coordinador;
                }
            }

            if (coordinadores == null) {
                _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_OBTENER_COORDINADORES);
                coordinadoresSinEstudios = new CoordinadorDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            coordinadoresSinEstudios = new CoordinadorDTO[0];
        }

        return coordinadoresSinEstudios;
    }

    /**
     * Modifica un coordinador.
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
                _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_EDIT_COORDINADOR.toString());
            } else {
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_EDIT_PASSWORD.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Crea un nuevo coordinador.
     *
     * @param _coordinador
     * @param _session
     * @return
     */
    public int addCoordinador(CoordinadorDTO _coordinador, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerCoordinadorRestService coorclient = new ServerCoordinadorRestService();
            statusServer = coorclient.create(_coordinador);
            coorclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_ADD_COORDINADOR.toString());
            } else {
                System.out.println(statusServer);
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_ADD_COORDINADOR.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Elimina un coordinador.
     *
     * @param _numIdentificacion
     * @param _session
     * @return
     */
    public int deleteCoordinador(String _numIdentificacion, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerCoordinadorRestService coorclient = new ServerCoordinadorRestService();
            statusServer = coorclient.remove(_numIdentificacion);
            coorclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_DELETE_COORDINADOR.toString());
            } else {
                System.out.println(statusServer);
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_DELETE_COORDINADOR.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorCoordinador", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Obtiene un coordinador.
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
     * Obtiene coordinadores por su correo.
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

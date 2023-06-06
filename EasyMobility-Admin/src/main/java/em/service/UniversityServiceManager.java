/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: UniversityServiceManager.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.UniversidadDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.rest.client.ServerUniversidadesRestService;
import javax.servlet.http.HttpSession;

public class UniversityServiceManager {

    private static UniversityServiceManager moiMeme = null; //Singleton

    /**
     * Construye un UniversityServiceManager.
     *
     */
    public UniversityServiceManager() {

    }

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return UniversityServiceManager
     */
    public static UniversityServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new UniversityServiceManager();
        }
        return moiMeme;
    }

    /**
     * Da de alta una nueva universidad.
     * 
     * @param _universidad
     * @param _session
     * @return 
     */
    public int addUniversidad(UniversidadDTO _universidad, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerUniversidadesRestService unclient = new ServerUniversidadesRestService();
            statusServer = unclient.create(_universidad);
            unclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorUniversidad", ErrorMessages.ERROR_ADD_UNIVERSIDAD.toString());
            } else {
                statusServer = 0;
                _session.setAttribute("exitoUniversidad", SuccessMessages.EXITO_ADD_UNIVERSIDAD.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorUniversidad", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }
    
    /**
     * Obtiene una universidad.
     * 
     * @param _id
     * @param _session
     * @return 
     */
    public UniversidadDTO getUniversidad(Long _id, HttpSession _session) {
        UniversidadDTO universidad = new UniversidadDTO();

        try {
            ServerUniversidadesRestService unClient = new ServerUniversidadesRestService();

            universidad = unClient.find(UniversidadDTO.class, String.valueOf(_id));
            unClient.close();

            if (universidad == null) {
                _session.setAttribute("errorUniversidad", ErrorMessages.ERROR_OBTENER_UNIVERSIDADES);
                universidad = new UniversidadDTO();
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorUniversidad", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            universidad = new UniversidadDTO();
        }

        return universidad;
    }
    
    /**
     * Devuelve lista de universidades.
     * 
     * @param _session
     * @return 
     */
    public UniversidadDTO[] getUniversidades(HttpSession _session) {
        UniversidadDTO[] universidades = new UniversidadDTO[0];

        try {
            ServerUniversidadesRestService emclient = new ServerUniversidadesRestService();

            universidades = emclient.findAll(UniversidadDTO[].class);
            emclient.close();

            for (UniversidadDTO universidad : universidades) {
            }

            if (universidades == null) {
                _session.setAttribute("errorUniversidad", ErrorMessages.ERROR_OBTENER_UNIVERSIDADES);
                universidades = new UniversidadDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorUniversidad", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            universidades = new UniversidadDTO[0];
        }

        return universidades;
    }

    /**
     * Devuelve una lista con las universidades de un país concreto.
     * 
     * @param _nombrePais
     * @param _session
     * @return 
     */
    public UniversidadDTO[] getUniversidadesPorPais(String _nombrePais, HttpSession _session) {
        UniversidadDTO[] universidadesPais = new UniversidadDTO[0];

        try {
            ServerUniversidadesRestService emclient = new ServerUniversidadesRestService();

            universidadesPais = emclient.findByCountry(UniversidadDTO[].class, _nombrePais);
            emclient.close();

            if (universidadesPais == null) {
                _session.setAttribute("errorUniversidad", ErrorMessages.ERROR_OBTENER_UNIVERSIDADES);
                universidadesPais = new UniversidadDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorUniversidad", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            universidadesPais = new UniversidadDTO[0];
        }

        return universidadesPais;
    }
}

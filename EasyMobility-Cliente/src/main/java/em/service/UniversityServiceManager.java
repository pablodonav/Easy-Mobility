/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: UniversityServiceManager.java
    Date: 5 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.service;

import em.common.dto.UniversidadDTO;
import em.common.enums.ErrorMessages;
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
     * Devuelve la lista completa de universidades.
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
                System.out.println("UNIVERSIDAD --> " + universidad.toString());
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
     * Devuelve la lista de universidades cuyo pais de localizacion es _nombrePais.
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

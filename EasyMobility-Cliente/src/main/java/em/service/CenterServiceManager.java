/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CenterServiceManager.java
    Date: 5 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.service;

import em.common.dto.CentroDTO;
import em.common.enums.ErrorMessages;
import em.rest.client.ServerCentrosRestService;
import javax.servlet.http.HttpSession;

public class CenterServiceManager {

    private static CenterServiceManager moiMeme = null; //Singleton

    /**
     * Construye un CenterServiceManager.
     * 
     */
    public CenterServiceManager() {

    }

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return CenterServiceManager
     */
    public static CenterServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new CenterServiceManager();
        }
        return moiMeme;
    }

    /**
     * Devuelve todos los centros dados de alta.
     * 
     * @param _session
     * @return 
     */
    public CentroDTO[] getCentros(HttpSession _session) {
        CentroDTO[] centros = new CentroDTO[0];

        try {
            ServerCentrosRestService emclient = new ServerCentrosRestService();

            centros = emclient.findAll(CentroDTO[].class);
            emclient.close();

            for (CentroDTO centro : centros) {
                System.out.println("CENTRO --> " + centro.toString());
            }

            if (centros == null) {
                _session.setAttribute("errorCentro", ErrorMessages.ERROR_OBTENER_CENTROS);
                centros = new CentroDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorCentro", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            centros = new CentroDTO[0];
        }

        return centros;
    }
}

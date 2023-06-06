/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CenterServiceManager.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.CentroDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
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
     * Da de alta un nuevo centro.
     *
     * @param _centro
     * @param _session
     * @return
     */
    public int addCentro(CentroDTO _centro, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerCentrosRestService cenClient = new ServerCentrosRestService();
            statusServer = cenClient.create(_centro);
            cenClient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorCentro", ErrorMessages.ERROR_ADD_CENTRO.toString());
            } else {
                statusServer = 0;
                _session.setAttribute("exitoCentro", SuccessMessages.EXITO_ADD_CENTRO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorCentro", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

    /**
     * Devuelve una lista con centros.
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

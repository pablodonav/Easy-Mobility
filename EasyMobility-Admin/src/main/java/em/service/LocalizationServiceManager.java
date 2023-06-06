/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: LocalizationServiceManager.java
    Date: 5 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.LocalizacionDTO;
import em.common.enums.ErrorMessages;
import em.rest.client.ServerLocalizacionRestService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class LocalizationServiceManager {

    private static LocalizationServiceManager moiMeme = null; //Singleton

    /**
     * Construye un LocalizationServiceManager
     *
     */
    public LocalizationServiceManager() {

    }

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return CenterServiceManager
     */
    public static LocalizationServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new LocalizationServiceManager();
        }
        return moiMeme;
    }

    /**
     * Devuelve la lista de paises únicos.
     *
     * @param _session
     * @return
     */
    public String[] getPaises(HttpSession _session) {
        List<String> paises = new ArrayList<String>();

        try {
            ServerLocalizacionRestService emclient = new ServerLocalizacionRestService();

            LocalizacionDTO[] localizaciones = emclient.findAll(LocalizacionDTO[].class);
            emclient.close();

            if (localizaciones == null) {
                _session.setAttribute("errorLocalizacion", ErrorMessages.ERROR_OBTENER_PAISES);
                paises = new ArrayList<String>();
            } else {
                for (LocalizacionDTO localizacion : localizaciones) {
                    System.out.println("PAIS --> " + localizacion.getPais());
                    paises.add(localizacion.getPais());
                }
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorLocalizacion", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            paises = new ArrayList<String>();
        }

        return paises.toArray(new String[paises.size()]);
    }
}

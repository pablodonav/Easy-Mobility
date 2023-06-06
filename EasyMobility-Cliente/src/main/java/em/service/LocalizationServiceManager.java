/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: LocalizationServiceManager.java
    Date: 5 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.service;

import em.common.dto.LocalizacionDTO;
import em.common.enums.ErrorMessages;
import em.rest.client.ServerLocalizacionRestService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ususario
 */
public class LocalizationServiceManager {

    private static LocalizationServiceManager moiMeme = null; //Singleton

    /**
     * Construye un LocalizationServiceManager.
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
     * Devuelve todos los paises dados de alta.
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
                for (LocalizacionDTO localizacion: localizaciones) {
                    if (!paises.contains(localizacion.getPais())){ // Evita países duplicados
                        paises.add(localizacion.getPais());
                    }
                }
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorLocalizacion", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            paises = new ArrayList<String>();
        }

        return paises.toArray(new String[paises.size()]);
    }
}

/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: LanguageServiceManager.java
    Date: 06 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.service;

import em.common.dto.IdiomaDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.rest.client.ServerIdiomasRestService;
import javax.servlet.http.HttpSession;

public class LanguageServiceManager {

    private static LanguageServiceManager moiMeme = null; //Singleton

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return LanguageServiceManager
     */
    public static LanguageServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new LanguageServiceManager();
        }
        return moiMeme;
    }

    /**
     * Método que devuelve los idiomas dados de alta.
     * 
     * @param _session
     * @return 
     */
    public IdiomaDTO[] getIdiomas(HttpSession _session) {
        IdiomaDTO[] idiomas = new IdiomaDTO[0];

        try {
            ServerIdiomasRestService eclient = new ServerIdiomasRestService();

            idiomas = eclient.findAll(IdiomaDTO[].class);
            eclient.close();

            if (idiomas == null) {
                _session.setAttribute("errorIdioma", ErrorMessages.ERROR_OBTENER_IDIOMAS);
                idiomas = new IdiomaDTO[0];
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorIdioma", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            idiomas = new IdiomaDTO[0];
        }

        return idiomas;
    }

    /**
     * Método que devuelve el idioma con id = _id.
     * 
     * @param _id
     * @param _session
     * @return 
     */
    public IdiomaDTO getIdioma(String _id, HttpSession _session) {
        IdiomaDTO idioma = new IdiomaDTO();

        try {
            ServerIdiomasRestService eclient = new ServerIdiomasRestService();

            idioma = eclient.find(IdiomaDTO.class, _id);
            eclient.close();

            if (idioma == null) {
                _session.setAttribute("errorIdioma", ErrorMessages.ERROR_OBTENER_IDIOMA);
                idioma = new IdiomaDTO();
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorIdioma", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            idioma = new IdiomaDTO();
        }

        return idioma;
    }

    /**
     * Crea un nuevo idioma.
     * 
     * @param _idioma
     * @param _session
     * @return 
     */
    public int addLanguage(IdiomaDTO _idioma, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerIdiomasRestService idclient = new ServerIdiomasRestService();
            statusServer = idclient.create(_idioma);
            idclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                System.out.println(statusServer);
                _session.setAttribute("errorIdioma", ErrorMessages.ERROR_ADD_IDIOMA.toString());
            } else {
                System.out.println("IDIOMA -->" + statusServer);
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_ADD_IDIOMA.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorIdioma", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

}

/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: StudyAreaServiceManager.java
    Date: 06 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.AreaEstudiosDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.rest.client.ServerAreaEstudiosRestService;
import javax.servlet.http.HttpSession;

public class StudyAreaServiceManager {

    private static StudyAreaServiceManager moiMeme = null; //Singleton

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return LanguageServiceManager
     */
    public static StudyAreaServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new StudyAreaServiceManager();
        }
        return moiMeme;
    }

    /**
     * Devuelve las areas de estudio.
     * 
     * @param _session
     * @return 
     */
    public AreaEstudiosDTO[] getAreasEstudio(HttpSession _session) {
        AreaEstudiosDTO[] areasEstudio = new AreaEstudiosDTO[0];

        try {
            ServerAreaEstudiosRestService aeclient = new ServerAreaEstudiosRestService();

            areasEstudio = aeclient.findAll(AreaEstudiosDTO[].class);
            
            aeclient.close();

            if (areasEstudio == null) {
                areasEstudio = new AreaEstudiosDTO[0];
            }
        } catch (RuntimeException exception) {
            areasEstudio = new AreaEstudiosDTO[0];
        }

        return areasEstudio;
    }

    /**
     * Da de alta un nuevo area de estudio.
     * 
     * @param _areaEstudios
     * @param _session
     * @return 
     */
    public int addAreaEstudios(AreaEstudiosDTO _areaEstudios, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerAreaEstudiosRestService aeclient = new ServerAreaEstudiosRestService();
            statusServer = aeclient.create(_areaEstudios);
            aeclient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                System.out.println(statusServer);
                _session.setAttribute("errorAreaEstudio", ErrorMessages.ERROR_ADD_AREA_ESTUDIO.toString());
            } else {
                statusServer = 0;
                _session.setAttribute("exitoAreaEstudio", SuccessMessages.EXITO_ADD_AREA_ESTUDIO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAreaEstudio", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }

}

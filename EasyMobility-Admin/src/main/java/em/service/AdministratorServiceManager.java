/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AdministratorServiceManager.java
    Date: 06 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.AdministradorDTO;
import em.common.enums.ErrorMessages;
import em.rest.client.ServerAdministradorRestService;
import javax.servlet.http.HttpSession;

public class AdministratorServiceManager {

    private static AdministratorServiceManager moiMeme = null; //Singleton

    /**
     * Construye un AdministratorServiceManager.
     *
     */
    public AdministratorServiceManager() {

    }

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return AdministratorServiceManager
     */
    public static AdministratorServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new AdministratorServiceManager();
        }
        return moiMeme;
    }

    /**
     * Devuelve el administrador asociado a _numIdentificacion.
     *
     * @param _numIdentificacion
     * @param _session
     * @return
     */
    public AdministradorDTO getAdministrador(String _numIdentificacion, HttpSession _session) {
        AdministradorDTO administrator = new AdministradorDTO();

        try {
            ServerAdministradorRestService admclient = new ServerAdministradorRestService();

            administrator = admclient.find(AdministradorDTO.class, _numIdentificacion);
            admclient.close();

            System.out.println("ADMINISTRATOR LOGIN ==> " + administrator);

            if (administrator == null) {
                _session.setAttribute("errorAdministrator", ErrorMessages.ERROR_OBTENER_ADMINISTRADOR);
                administrator = new AdministradorDTO();
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorAdministrator", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
            administrator = new AdministradorDTO();
        }

        return administrator;
    }

}

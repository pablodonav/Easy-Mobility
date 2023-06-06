/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: FileServiceManager.java
    Date: 06 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.service;

import em.common.dto.FicheroDTO;
import em.common.enums.ErrorMessages;
import em.common.enums.SuccessMessages;
import em.rest.client.ServerFicherosRestService;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

public class FileServiceManager {

    private static FileServiceManager moiMeme = null; //Singleton

    /**
     * Método que retorna una instancia de la clase Singleton
     *
     * @return StudyServiceManager
     */
    public static FileServiceManager getInstance() {
        if (moiMeme == null) {
            moiMeme = new FileServiceManager();
        }
        return moiMeme;
    }
    
    /**
     * Devuelve los ficheros de un alumno con numIdentificacion = _numIdentificador;
     * 
     * @param _numIdentificador
     * @param _session
     * @return 
     */
    public List<FicheroDTO> getFicheroByAlumn(String _numIdentificador, HttpSession _session) {
        List<FicheroDTO> ficheros = null;

        try {
            ServerFicherosRestService fileClient = new ServerFicherosRestService();

            ficheros = Arrays.asList(fileClient.findFilesByAlumn(FicheroDTO[].class, _numIdentificador));
            fileClient.close();

            if (ficheros.isEmpty()) {
                _session.setAttribute("errorFicheros", ErrorMessages.ERROR_OBTENER_FICHEROS);
            }
        } catch (RuntimeException exception) {
            _session.setAttribute("errorFicheros", ErrorMessages.ERROR_OBTENER_FICHEROS.toString());
            exception.printStackTrace();
        }

        return ficheros;
    }
    
    /**
     * Elimina el fichero con id = _fileId.
     * 
     * @param _fileId
     * @param _session
     * @return 
     */
    public int deleteFile(Long _fileId, HttpSession _session) {
        int statusServer = 0;

        try {
            ServerFicherosRestService fileClient = new ServerFicherosRestService();
            statusServer = fileClient.remove(_fileId);
            fileClient.close();

            if (String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_400.toString())
                    || String.valueOf(statusServer).startsWith(ErrorMessages.STATUS_NOK_GROUP_500.toString())) {
                _session.setAttribute("errorFichero", ErrorMessages.ERROR_BORRAR_FICHERO.toString());
            } else {
                System.out.println(statusServer);
                statusServer = 0;
                _session.setAttribute("exito", SuccessMessages.EXITO_DELETE_FICHERO.toString());
            }

        } catch (RuntimeException exception) {
            _session.setAttribute("errorAlumno", ErrorMessages.ERROR_CONEXION_CON_SERVIDOR.toString());
        }
        return statusServer;
    }
    
}

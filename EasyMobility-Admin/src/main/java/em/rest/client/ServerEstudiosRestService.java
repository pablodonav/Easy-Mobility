/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ServerEstudiosRestService.java
    Date: 06 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ServerEstudiosRestService {

    private WebTarget webTarget;
    private Client client;
    
    private final Config config = ConfigProvider.getConfig();
    
    private final String BASE_URI = this.config.getValue("em.urlServidor", String.class);

    /**
     * Construye un ServerEstudiosRestService.
     * 
     */
    public ServerEstudiosRestService() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("study");
    }

    /**
     * Devuelve la cuenta de estudios.
     * 
     * @return
     * @throws RuntimeException 
     */
    public String countREST() throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN)
                .get(String.class);
    }

    /**
     * Modifica un estudio.
     * 
     * @param _requestEntity
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @return
     * @throws RuntimeException 
     */
    public int edit(Object _requestEntity, String _idCentro, String _idUniversidad, String _idEstudio) throws RuntimeException {
        return webTarget.path(java.text.MessageFormat.format("{0}/{1}/{2}", new Object[]{_idCentro, _idUniversidad, _idEstudio}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(_requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON))
                .getStatus();
    }

    /**
     * Encuentra un estudio.
     * 
     * @param <T>
     * @param _responseType
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @return
     * @throws RuntimeException 
     */
    public <T> T find(Class<T> _responseType, String _idCentro, String _idUniversidad, String _idEstudio) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}/{2}", new Object[]{_idCentro, _idUniversidad, _idEstudio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Encuentra estudios por coordinador.
     * 
     * @param <T>
     * @param _responseType
     * @param _numIdentificacion
     * @return
     * @throws RuntimeException 
     */
    public <T> T findEstudioByCoordinador(Class<T> _responseType, String _numIdentificacion) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("studyByCoordinator/{0}", new Object[]{_numIdentificacion}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Devuelve estudios dentro de un rango.
     * 
     * @param <T>
     * @param _responseType
     * @param _from
     * @param _to
     * @return
     * @throws RuntimeException 
     */
    public <T> T findRange(Class<T> _responseType, String _from, String _to) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{_from, _to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Crea un estudio.
     * 
     * @param _requestEntity
     * @return
     * @throws RuntimeException 
     */
    public int create(Object _requestEntity) throws RuntimeException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(_requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON))
                .getStatus();
    }

    /**
     * Devuelve todos los estudios.
     * 
     * @param <T>
     * @param _responseType
     * @return
     * @throws RuntimeException 
     */
    public <T> T findAll(Class<T> _responseType) throws RuntimeException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Elimina un estudio.
     * 
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @return
     * @throws RuntimeException 
     */
    public int remove(String _idCentro, String _idUniversidad, String _idEstudio) throws RuntimeException {
        return webTarget.path(java.text.MessageFormat.format("{0}/{1}/{2}", new Object[]{_idCentro, _idUniversidad, _idEstudio}))
                .request()
                .delete()
                .getStatus();
    }

    /**
     * Devuelve los estudios ajenos a un coordinador.
     * 
     * @param <T>
     * @param _responseType
     * @param _numIdentificacion
     * @return
     * @throws RuntimeException 
     */
    public <T> T findDifferentStudies(Class<T> _responseType, String _numIdentificacion) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path("estudios-ajenos-coordinador");
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{_numIdentificacion}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Cierra la comunicación.
     * 
     */
    public void close() {
        client.close();
    }

}

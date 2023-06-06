/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ServerAlumnosRestService.java
    Date: 05 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.rest.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ServerAlumnosRestService {

    private WebTarget webTarget;
    private Client client;
    
    private final Config config = ConfigProvider.getConfig();
    
    private final String BASE_URI = this.config.getValue("em.urlServidor", String.class);

    /**
     * Construye un ServerAlumnosRestService.
     * 
     */
    public ServerAlumnosRestService() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("student");
    }

    /**
     * Devuelve la cuenta de alumnos.
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
     * Modifica un alumno.
     * 
     * @param _requestEntity
     * @param _numIdentificacion
     * @return
     * @throws RuntimeException 
     */
    public int edit(Object _requestEntity, String _numIdentificacion) throws RuntimeException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{_numIdentificacion}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(_requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON))
                .getStatus();
    }

    /**
     * Encuentra un alumno en concreto.
     * 
     * @param <T>
     * @param _responseType
     * @param _numIdentificacion
     * @return
     * @throws RuntimeException 
     */
    public <T> T find(Class<T> _responseType, String _numIdentificacion) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{_numIdentificacion}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Encuentra un alumno por su correo.
     * 
     * @param <T>
     * @param _responseType
     * @param _email
     * @return
     * @throws ClientErrorException 
     */
    public <T> T findByEmail(Class<T> _responseType, String _email) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("email/{0}", new Object[]{_email}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Encuentra un rango de alumnos.
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
     * Crea un alumno.
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
     * Devuelve todos los alumnos.
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
     * Elimina un alumno.
     * 
     * @param _numIdentificacion
     * @return
     * @throws RuntimeException 
     */
    public int remove(String _numIdentificacion) throws RuntimeException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{_numIdentificacion}))
                .request()
                .delete()
                .getStatus();
    }

    /**
     * Cierra la comunicación.
     * 
     */
    public void close() {
        client.close();
    }

}

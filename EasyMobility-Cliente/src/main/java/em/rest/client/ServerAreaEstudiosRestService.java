/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ServerAreaEstudiosRestService.java
    Date: 06 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ServerAreaEstudiosRestService {

    private WebTarget webTarget;
    private Client client;
    
    private final Config config = ConfigProvider.getConfig();
    
    private final String BASE_URI = this.config.getValue("em.urlServidor", String.class);

    /**
     * Construye un ServerAreaEstudiosRestService.
     * 
     */
    public ServerAreaEstudiosRestService() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("studyField");
    }

    /**
     * Devuelve la cuenta de areas de estudio.
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
     * Modifica un area de etudio.
     * 
     * @param _requestEntity
     * @param _id
     * @return
     * @throws RuntimeException 
     */
    public int edit(Object _requestEntity, String _id) throws RuntimeException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{_id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(_requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON))
                .getStatus();
    }

    /**
     * Encuentra un area de estudio.
     * 
     * @param <T>
     * @param _responseType
     * @param _id
     * @return
     * @throws RuntimeException 
     */
    public <T> T find(Class<T> _responseType, String _id) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Encuentra un rango de areas de estudio.
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
     * Crea un area de estudio.
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
     * Devuelve todas las areas de estudio.
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
     * Elimina un area de estudio.
     * 
     * @param _id
     * @return
     * @throws RuntimeException 
     */
    public int remove(String _id) throws RuntimeException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{_id}))
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

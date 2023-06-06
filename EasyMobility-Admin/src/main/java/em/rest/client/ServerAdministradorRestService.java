/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ServerAdministradorRestService.java
    Date: 05 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.rest.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ServerAdministradorRestService {

    private WebTarget webTarget;
    private Client client;

    private final Config config = ConfigProvider.getConfig();

    private final String BASE_URI = this.config.getValue("em.urlServidor", String.class);

    /**
     * Construye un ServerAdministradorRestService.
     *
     */
    public ServerAdministradorRestService() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("administrator");
    }

    /**
     * Devuelve la cuenta de administradores.
     *
     * @return
     * @throws ClientErrorException
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Modifica un administrador.
     *
     * @param _requestEntity
     * @param _numIdentificacion
     * @throws ClientErrorException
     */
    public void edit(Object _requestEntity, String _numIdentificacion) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{_numIdentificacion}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(_requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra un administrador.
     *
     * @param <T>
     * @param _responseType
     * @param _numIdentificacion
     * @return
     * @throws ClientErrorException
     */
    public <T> T find(Class<T> _responseType, String _numIdentificacion) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{_numIdentificacion}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(_responseType);
    }

    /**
     * Encuentra administradores dentro de un rango.
     *
     * @param <T>
     * @param _responseType
     * @param _from
     * @param _to
     * @return
     * @throws ClientErrorException
     */
    public <T> T findRange(Class<T> _responseType, String _from, String _to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{_from, _to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Crea un administrador.
     *
     * @param _requestEntity
     * @throws ClientErrorException
     */
    public void create(Object _requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(_requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Devuelve todos los administradores.
     *
     * @param <T>
     * @param _responseType
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAll(Class<T> _responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Elimina un administrador.
     *
     * @param _numIdentificacion
     * @throws ClientErrorException
     */
    public void remove(String _numIdentificacion) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{_numIdentificacion}))
                .request()
                .delete();
    }

    /**
     * Cierra la comunicación.
     *
     */
    public void close() {
        client.close();
    }
}

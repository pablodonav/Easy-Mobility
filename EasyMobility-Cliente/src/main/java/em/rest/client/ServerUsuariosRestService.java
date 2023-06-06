/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ServerUsuariosRestService.java
    Date: 05 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.rest.client;

import em.common.dto.UsuarioDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ServerUsuariosRestService {

    private WebTarget webTarget;
    private Client client;
    
    private final Config config = ConfigProvider.getConfig();
    
    private final String BASE_URI = this.config.getValue("em.urlServidor", String.class);

    /**
     * Construye un ServerUsuariosRestService.
     * 
     */
    public ServerUsuariosRestService() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("user");
    }

    /**
     * Crea un nuevo usuario.
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
     * Modifica un usuario.
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
     * Elimina un usuario.
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
     * Encuentra un usuario.
     * 
     * @param _id
     * @return
     * @throws RuntimeException 
     */
    public UsuarioDTO find(String _id) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(UsuarioDTO.class);
    }

    /**
     * Encuentra usuarios dentro de un rango.
     * 
     * @param _from
     * @param _to
     * @return
     * @throws RuntimeException 
     */
    public UsuarioDTO findRange(String _from, String _to) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{_from, _to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(UsuarioDTO.class);
    }

    /**
     * Devuelve todos los usuarios.
     * 
     * @return
     * @throws RuntimeException 
     */
    public UsuarioDTO[] findAll() throws RuntimeException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(UsuarioDTO[].class);
    }

    /**
     * Devuelve la cuenta de usuarios.
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
     * Devuelve el tipo de un usuario.
     * 
     * @param _id
     * @return
     * @throws RuntimeException 
     */
    public String findUserType(String _id) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path("userType");
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{_id}));
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN)
                .get(String.class);
    }

    /**
     * Cierra la comunicación.
     * 
     */
    public void close() {
        client.close();
    }
}

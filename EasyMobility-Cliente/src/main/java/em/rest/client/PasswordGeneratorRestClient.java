/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: PasswordGeneratorRestClient.java
    Date: 06 abr. 2023
  
    Authors: Adnana Dragut
 */
package em.rest.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class PasswordGeneratorRestClient {

    private static final int RANDOM_PWD_LENGTH = 8;
    private static final int LOWER_CHARS = 1;
    private static final int UPPER_CHARS = 1;
    private static final int INT_CHARS = 1;
    private static final int SPECIAL_CHARS = 0;
    
    private final Config config = ConfigProvider.getConfig();
    
    private WebTarget webTarget;
    private Client client;
    private final String BASE_URL = this.config.getValue("em.urlPsswrd", String.class);

    /**
     * Construye un PasswordGeneratorRestClient.
     * 
     */
    public PasswordGeneratorRestClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }

    /**
     * Obtiene una password aleatoria.
     * 
     * @return
     * @throws ClientErrorException 
     */
    public String getRandomPassword() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.queryParam("length", RANDOM_PWD_LENGTH).queryParam("lower", LOWER_CHARS).queryParam("upper", UPPER_CHARS).queryParam("int", INT_CHARS).queryParam("special", SPECIAL_CHARS);
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
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

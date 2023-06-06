/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ServerAcuerdoEstudiosRestService.java
    Date: 06 abr. 2023
  
    Authors: Pablo Doñate
 */
package em.rest.client;

import em.common.dto.AcuerdoEstudiosDTO;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ServerAcuerdoEstudiosRestService {

    private WebTarget webTarget;
    private Client client;

    private final Config config = ConfigProvider.getConfig();

    private final String BASE_URI = this.config.getValue("em.urlServidor", String.class);

    /**
     * Construye un ServerAcuerdoEstudiosRestService.
     *
     */
    public ServerAcuerdoEstudiosRestService() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("studyAgreement");
    }

    /**
     * Encuentra alumnos por coordinador.
     *
     * @param <T>
     * @param _responseType
     * @param _numIdentificacion
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAlumnosByCoordinador(Class<T> _responseType, String _numIdentificacion) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("studentsByCoordinator/{0}", new Object[]{_numIdentificacion}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Devuelve la cuenta de acuerdos.
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
     * Modifica un acuerdo.
     *
     * @param _requestEntity
     * @param _idAcuerdo
     * @return
     * @throws RuntimeException
     */
    public int edit(Object _requestEntity, String _idAcuerdo) throws RuntimeException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{_idAcuerdo}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(_requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON))
                .getStatus();
    }

    /**
     * Encuentra un acuerdo.
     *
     * @param <T>
     * @param _responseType
     * @param _idAcuerdo
     * @return
     * @throws RuntimeException
     */
    public <T> T find(Class<T> _responseType, String _idAcuerdo) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{_idAcuerdo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Encuentra un acuerdo en un rango.
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
     * Encuentra alumnos de un estudio.
     *
     * @param <T>
     * @param _responseType
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @return
     * @throws RuntimeException
     */
    public <T> T findAlumnosEstudio(Class<T> _responseType, String _idCentro, String _idUniversidad, String _idEstudio) throws RuntimeException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}/{2}", new Object[]{_idCentro, _idUniversidad, _idEstudio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(_responseType);
    }

    /**
     * Crea un acuerdo.
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
     * Devuelve todos los acuerdos.
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
     * Elimina un acuerdo.
     *
     * @param _idAcuerdo
     * @return
     * @throws RuntimeException
     */
    public int remove(String _idAcuerdo) throws RuntimeException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{_idAcuerdo}))
                .request()
                .delete()
                .getStatus();
    }

    /**
     * Encuentra el acuerdo de un alumno.
     *
     * @param _numIdentificacion
     * @return
     * @throws ClientErrorException
     */
    public AcuerdoEstudiosDTO findAcuerdoAlumno(String _numIdentificacion) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("alumn/{0}", new Object[]{_numIdentificacion}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(AcuerdoEstudiosDTO.class);
    }

    /**
     * Cierra la comunicación.
     *
     */
    public void close() {
        client.close();
    }

}

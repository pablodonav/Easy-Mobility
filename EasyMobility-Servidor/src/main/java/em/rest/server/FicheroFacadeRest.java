/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: FicheroFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.Mensaje;
import em.bd.Fichero;
import em.websocket.WebSocketManager;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("file")
public class FicheroFacadeRest extends AbstractFacade<Fichero> {

    @PersistenceContext(unitName = "EasyMobiliy-PU")
    private EntityManager em;

    @EJB
    WebSocketManager wsm;

    /**
     * Devuelve el EntityManager.
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Construye un FicheroFacadeRest
     *
     */
    public FicheroFacadeRest() {
        super(Fichero.class);
    }

    /**
     * Devuelve en formato JSON los datos del fichero.
     *
     * @param _data
     * @return
     */
    private String createJsonWithFileData(Fichero _data) {
        String jsonNuevoFichero = "{\"coordinadorDestinoId\": \"" + _data.getAcuerdo().getEstudioDestino().getCoordinador().getNumIdentificacion()
                + "\", \"coordinadorOrigenId\": \"" + _data.getAcuerdo().getEstudioOrigen().getCoordinador().getNumIdentificacion()
                + "\", \"alumnoId\": \"" + _data.getAcuerdo().getAlumno().getNumIdentificacion()
                + "\", \"file\": {\"fileId\": \"" + _data.getId()
                + "\", \"fileName\": \"" + _data.getFileName()
                + "\", \"fileMimeType\": \"" + _data.getMimeType() + "\"}}";

        return jsonNuevoFichero;
    }

    /**
     * Crea un nuevo fichero.
     *
     * @param _entity
     */
    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Fichero _entity) {
        super.create(_entity);

        // Se notifica a todos los coordinadores y alumnos conectados de la inserción de un nuevo documento de un alumno
        String jsonNuevoFichero = createJsonWithFileData(_entity);
        Mensaje msg = new Mensaje(jsonNuevoFichero);
        msg.setCmd("new-file");
        wsm.sendToClients(msg.toJson());
    }

    /**
     * Encuentra ficheros por alumno.
     *
     * @param _numIdentificacion
     * @return
     */
    @GET
    @Path("{numIdentificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fichero> findFilesByAlumn(@PathParam("numIdentificacion") String _numIdentificacion) {
        List<Fichero> ficheros = super.findAll();
        List<Fichero> ficherosAlumno = new ArrayList<>();
        for (Fichero fichero : ficheros) {
            if (fichero.getAcuerdo().getAlumno().getNumIdentificacion() == null ? _numIdentificacion == null : fichero.getAcuerdo().getAlumno().getNumIdentificacion().equals(_numIdentificacion)) {
                ficherosAlumno.add(fichero);
            }
        }
        return ficherosAlumno;
    }

    /**
     * Devuelve todos los ficheros de un alumno.
     *
     * @return
     */
    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fichero> findAll() {
        return super.findAll();
    }

    /**
     * Encuentra un fichero.
     *
     * @param _id
     * @return
     */
    @GET
    @Path("file/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Fichero find(@PathParam("id") Long _id) {
        return super.find(_id);
    }

    /**
     * Devuelve en formato JSON los datos del fichero borrado.
     *
     * @param _data
     * @return
     */
    private String createJsonWithFileRemovedData(Fichero _data) {
        String jsonFicheroEliminado = "{\"ficheroEliminadoId\": \"" + _data.getId()
                + "\", \"coordinadorOrigenId\": \"" + _data.getAcuerdo().getEstudioOrigen().getCoordinador().getNumIdentificacion()
                + "\", \"coordinadorDestinoId\": \"" + _data.getAcuerdo().getEstudioDestino().getCoordinador().getNumIdentificacion()
                + "\", \"alumnoId\": \"" + _data.getAcuerdo().getAlumno().getNumIdentificacion()
                + "\"}";

        return jsonFicheroEliminado;
    }

    /**
     * Elimina un fichero.
     *
     * @param _id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long _id) {
        Fichero ficheroEliminado = super.find(_id);
        super.remove(ficheroEliminado);

        // Se notifica a todos los coordinadores y alumnos conectados de la eliminación del fichero de un alumno existente
        String jsonFicheroEliminado = createJsonWithFileRemovedData(ficheroEliminado);
        Mensaje msg = new Mensaje(jsonFicheroEliminado);
        msg.setCmd("remove-file");
        wsm.sendToClients(msg.toJson());
    }
}

/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AcuerdoEstudiosFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.Mensaje;
import em.bd.AcuerdoEstudios;
import em.bd.Alumno;
import em.websocket.WebSocketManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("studyAgreement")
public class AcuerdoEstudiosFacadeRest extends AbstractFacade<AcuerdoEstudios> {

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
     * Construye un AcuerdoEstudiosFacadeRest
     *
     */
    public AcuerdoEstudiosFacadeRest() {
        super(AcuerdoEstudios.class);
    }

    /**
     * Crea un JSON con los datos del alumno.
     *
     * @param _data
     * @param _coordinadorDestinoIdBeforeUpdate
     * @return
     */
    private String createJsonWithAlumnoData(AcuerdoEstudios _data, String _coordinadorDestinoIdBeforeUpdate) {
        String jsonNuevoAlumno = "{\"coordinadorDestinoId\": \"" + _data.getEstudioDestino().getCoordinador().getNumIdentificacion()
                + "\", \"coordinadorDestinoIdBeforeUpdate\": \"" + _coordinadorDestinoIdBeforeUpdate
                + "\", \"coordinadorOrigenId\": \"" + _data.getEstudioOrigen().getCoordinador().getNumIdentificacion()
                + "\", \"alumno\": {\"tipoId\": \"" + _data.getAlumno().getDocumentoIdentificacion().toString()
                + "\", \"numId\": \"" + _data.getAlumno().getNumIdentificacion()
                + "\"}, \"acuerdo\": {\"cursoAcademico\": \"" + _data.getCursoAcademico()
                + "\", \"areaEstudio\": \"" + _data.getEstudioOrigen().getAreaEstudios().getNombre()
                + "\", \"tipoEstudio\": \"out\", \"idioma\": \"" + _data.getIdioma().getNombre()
                + "\", \"periodoIntercambio\": \"" + _data.getPeriodoIntercambio().toString()
                + "\", \"paisAcuerdo\": \"" + _data.getEstudioDestino().getCentro().getUniversidad().getLocalizacion().getPais()
                + "\", \"universidadAcuerdo\": \"" + _data.getEstudioDestino().getCentro().getUniversidad().getNombre() + "\"}}";

        return jsonNuevoAlumno;
    }

    /**
     * Crea un acuerdo de estudios.
     *
     * @param _entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void create(AcuerdoEstudios _entity) {
        super.create(_entity);

        // Se notifica a todos los coordinadores conectados de la creación de un nuevo alumno con un nuevo acuerdo
        String jsonNuevoAlumno = createJsonWithAlumnoData(_entity, "");
        Mensaje msg = new Mensaje(jsonNuevoAlumno);
        msg.setCmd("new-alumno");
        System.out.println("ANTES DE BROADCAST");
        wsm.sendToClients(msg.toJson());
    }

    /**
     * Modifica un acuerdo de estudios.
     *
     * @param _idAcuerdo
     * @param _entity
     */
    @PUT
    @Path("{idAcuerdo}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("idAcuerdo") Long _idAcuerdo, AcuerdoEstudios _entity) {
        String idCoordinadorBeforeUpdate = this.find(_idAcuerdo).getEstudioDestino().getCoordinador().getNumIdentificacion();
        _entity.setId(_idAcuerdo);
        super.edit(_entity);

        // Se notifica a todos los coordinadores conectados de la edición de un alumno existente
        String jsonAlumnoEditado = createJsonWithAlumnoData(_entity, idCoordinadorBeforeUpdate);
        Mensaje msg = new Mensaje(jsonAlumnoEditado);
        msg.setCmd("edit-alumno");
        wsm.sendToClients(msg.toJson());
    }

    /**
     * Devuelve en JSON el alumno eliminado.
     *
     * @param _data
     * @return
     */
    private String createJsonWithAlumnoRemovedData(AcuerdoEstudios _data) {
        String jsonAlumnoEliminado = "{\"alumnoEliminadoId\": \"" + _data.getAlumno().getNumIdentificacion()
                + "\", \"coordinadorOrigenId\": \"" + _data.getEstudioOrigen().getCoordinador().getNumIdentificacion()
                + "\", \"coordinadorDestinoId\": \"" + _data.getEstudioDestino().getCoordinador().getNumIdentificacion()
                + "\"}";

        return jsonAlumnoEliminado;
    }

    /**
     * Elimina un acuerdo de estudios.
     *
     * @param _idAcuerdo
     */
    @DELETE
    @Path("{idAcuerdo}")
    public void remove(@PathParam("idAcuerdo") Long _idAcuerdo) {
        AcuerdoEstudios acuerdoEliminado = super.find(_idAcuerdo);
        super.remove(acuerdoEliminado);

        // Se notifica a todos los coordinadores conectados de la eliminación del acuerdo de un alumno existente (Cada vez que se elimina acuerdo también se elimina alumno)
        String jsonAlumnoEliminado = createJsonWithAlumnoRemovedData(acuerdoEliminado);
        Mensaje msg = new Mensaje(jsonAlumnoEliminado);
        msg.setCmd("remove-alumno");
        wsm.sendToClients(msg.toJson());
    }

    /**
     * Encuentra un acuerdo de estudios.
     *
     * @param _idAcuerdo
     * @return
     */
    @GET
    @Path("{idAcuerdo}")
    @Produces(MediaType.APPLICATION_JSON)
    public AcuerdoEstudios find(@PathParam("idAcuerdo") Long _idAcuerdo) {
        return super.find(_idAcuerdo);
    }

    /**
     * Encuentra alumnos de un estudio.
     *
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @return
     */
    @GET
    @Path("{idCentro}/{idUniversidad}/{idEstudio}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alumno> findAlumnosEstudio(@PathParam("idCentro") Long _idCentro,
            @PathParam("idUniversidad") Long _idUniversidad,
            @PathParam("idEstudio") Long _idEstudio) {
        List<AcuerdoEstudios> acuerdos = super.findAll();
        List<Alumno> alumnosEstudio = new ArrayList<>();
        for (AcuerdoEstudios acuerdo : acuerdos) {
            if ((Objects.equals(acuerdo.getEstudioOrigen().getId().getIdEstudio(), _idEstudio))
                    && (Objects.equals(acuerdo.getEstudioOrigen().getId().getIdCentro().getIdCentro(), _idCentro))
                    && (Objects.equals(acuerdo.getEstudioOrigen().getId().getIdCentro().getIdUniversidad(), _idUniversidad))) {
                alumnosEstudio.add(acuerdo.getAlumno());
            }
        }
        return alumnosEstudio;
    }

    /**
     * Encuentra alumnos por coordinador.
     *
     * @param _numIdentificacion
     * @return
     */
    @GET
    @Path("studentsByCoordinator/{numIdentificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alumno> findAlumnosByCoordinador(@PathParam("numIdentificacion") String _numIdentificacion) {
        List<AcuerdoEstudios> acuerdos = super.findAll();
        List<Alumno> alumnosCoordinador = new ArrayList<>();
        for (AcuerdoEstudios acuerdo : acuerdos) {
            if ((acuerdo.getEstudioOrigen().getCoordinador().getNumIdentificacion() == null ? _numIdentificacion == null : acuerdo.getEstudioOrigen().getCoordinador().getNumIdentificacion().equals(_numIdentificacion))
                    || (acuerdo.getEstudioDestino().getCoordinador().getNumIdentificacion() == null ? _numIdentificacion == null : acuerdo.getEstudioDestino().getCoordinador().getNumIdentificacion().equals(_numIdentificacion))) {
                alumnosCoordinador.add(acuerdo.getAlumno());
            }
        }
        return alumnosCoordinador;
    }

    /**
     * Devuelve el acuerdo de un alumno.
     *
     * @param _numIdentificacion
     * @return
     */
    @GET
    @Path("alumn/{numIdentificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public AcuerdoEstudios findAcuerdoAlumno(@PathParam("numIdentificacion") String _numIdentificacion) {
        List<AcuerdoEstudios> acuerdos = super.findAll();
        AcuerdoEstudios acuerdoEncontrado = null;
        for (AcuerdoEstudios acuerdo : acuerdos) {
            if (acuerdo.getAlumno().getNumIdentificacion().equals(_numIdentificacion)) {
                acuerdoEncontrado = acuerdo;
            }
        }
        return acuerdoEncontrado;
    }

    /**
     * Devuelve todos los acuerdos de estudio.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<AcuerdoEstudios> findAll() {
        return super.findAll();
    }

    /**
     * Devuelve los acuerdos de estudio en un rango determinado.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AcuerdoEstudios> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve la cuenta de acuerdos de estudio.
     *
     * @return
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

}

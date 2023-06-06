/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: EstudioFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.bd.AreaEstudios;
import em.bd.Centro;
import em.bd.Coordinador;
import em.bd.Estudio;
import em.bd.compositekeys.CompositeKeyCentro;
import em.bd.compositekeys.CompositeKeyEstudio;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
@Path("study")
public class EstudioFacadeRest extends AbstractFacade<Estudio> {

    @PersistenceContext(unitName = "EasyMobiliy-PU")
    private EntityManager em;

    @Inject
    private CentroFacadeRest centroFacadeRest;

    @Inject
    private CoordinadorFacadeRest coordinadorFacadeRest;

    @Inject
    private AreaEstudiosFacadeRest areaEstudiosFacadeRest;

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
     * Construye un EstudioFacadeRest
     *
     */
    public EstudioFacadeRest() {
        super(Estudio.class);
    }

    /**
     * Crea un nuevo estudio.
     *
     * @param _entity
     */
    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Estudio _entity) {
        TypedQuery<Long> query = em.createQuery("SELECT MAX(e.id.idEstudio) FROM Estudio e", Long.class);
        Long ultimoId = query.getSingleResult();
        if (ultimoId == null) {
            ultimoId = 1L;
        } else {
            ultimoId = ultimoId + 1L;
        }
        _entity.setId(new CompositeKeyEstudio(ultimoId, _entity.getId().getIdCentro()));
        EntityManager emCentro = centroFacadeRest.getEntityManager();
        EntityManager emCoordinador = coordinadorFacadeRest.getEntityManager();
        EntityManager emAreaEstudios = areaEstudiosFacadeRest.getEntityManager();
        Centro centro = emCentro.find(Centro.class, _entity.getCentro().getId());
        Coordinador coordinador = emCoordinador.find(Coordinador.class, _entity.getCoordinador().getNumIdentificacion());
        AreaEstudios areaEstudios = emAreaEstudios.find(AreaEstudios.class, _entity.getAreaEstudios().getId());
        _entity.setAreaEstudios(areaEstudios);
        _entity.setCentro(centro);
        _entity.setCoordinador(coordinador);
        super.create(_entity);
    }

    /**
     * Modifica un estudio.
     *
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @param _entity
     */
    @PUT
    @Path("{idCentro}/{idUniversidad}/{idEstudio}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("idCentro") Long _idCentro,
            @PathParam("idUniversidad") Long _idUniversidad,
            @PathParam("idEstudio") Long _idEstudio,
            Estudio _entity) {
        CompositeKeyEstudio id = new CompositeKeyEstudio(_idEstudio, new CompositeKeyCentro(_idCentro, _idUniversidad));
        _entity.setId(id);
        super.edit(_entity);
    }

    /**
     * Elimina un estudio.
     *
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     */
    @DELETE
    @Path("{idCentro}/{idUniversidad}/{idEstudio}")
    public void remove(@PathParam("idCentro") Long _idCentro,
            @PathParam("idUniversidad") Long _idUniversidad,
            @PathParam("idEstudio") Long _idEstudio) {
        CompositeKeyEstudio id = new CompositeKeyEstudio(_idEstudio, new CompositeKeyCentro(_idCentro, _idUniversidad));
        super.remove(super.find(id));
    }

    /**
     * Encuentra un estudio.
     *
     * @param _idCentro
     * @param _idUniversidad
     * @param _idEstudio
     * @return
     */
    @GET
    @Path("{idCentro}/{idUniversidad}/{idEstudio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Estudio find(@PathParam("idCentro") Long _idCentro,
            @PathParam("idUniversidad") Long _idUniversidad,
            @PathParam("idEstudio") Long _idEstudio) {
        CompositeKeyEstudio id = new CompositeKeyEstudio(_idEstudio, new CompositeKeyCentro(_idCentro, _idUniversidad));
        System.out.println("ESTUDIO ENCONTRADO SERVER ==> " + id.toJson());
        Estudio estudio = super.find(id);
        return estudio;
    }

    /**
     * Encuentra los estudios de un coordinador.
     *
     * @param _numIdentificacion
     * @return
     */
    @GET
    @Path("studyByCoordinator/{numIdentificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Estudio findEstudioByCoordinador(@PathParam("numIdentificacion") String _numIdentificacion) {
        List<Estudio> estudios = super.findAll();
        for (Estudio estudio : estudios) {
            if (estudio.getCoordinador().getNumIdentificacion().equals(_numIdentificacion)) {
                return estudio;
            }
        }
        return null;
    }

    /**
     * Devuelve todos los estudios.
     *
     * @return
     */
    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudio> findAll() {
        return super.findAll();
    }

    /**
     * Devuelve los estudios dentro de un rango en concreto.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudio> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve la cuenta de estudios.
     *
     * @return
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("estudios-ajenos-coordinador/{numIdentificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudio> findDifferentStudies(@PathParam("numIdentificacion") String _numIdentificacion) {
        List<Estudio> estudios = this.findAll();
        List<Estudio> estudiosAjenosACoordinador = new ArrayList<Estudio>();

        for (Estudio estudio : estudios) {
            if (!estudio.getCoordinador().getNumIdentificacion().equals(_numIdentificacion)) {
                estudiosAjenosACoordinador.add(estudio);
            }
        }

        return estudiosAjenosACoordinador;
    }

}

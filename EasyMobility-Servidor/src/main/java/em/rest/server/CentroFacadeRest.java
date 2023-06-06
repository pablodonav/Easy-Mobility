/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CentroFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.bd.Centro;
import em.bd.Universidad;
import em.bd.compositekeys.CompositeKeyCentro;
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
@Path("center")
public class CentroFacadeRest extends AbstractFacade<Centro> {

    @PersistenceContext(unitName = "EasyMobiliy-PU")
    private EntityManager em;

    @Inject
    private UniversidadFacadeRest universidadFacadeRest;

    /**
     * Devuelve el EntityManager.
     *
     * @return
     */
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Construye un CentroFacadeRest
     *
     */
    public CentroFacadeRest() {
        super(Centro.class);
    }

    /**
     * Crea un nuevo centro.
     *
     * @param _entity
     */
    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Centro _entity) {
        TypedQuery<Long> query = em.createQuery("SELECT MAX(c.id.idCentro) FROM Centro c", Long.class);
        Long ultimoId = query.getSingleResult();
        if (ultimoId == null) {
            ultimoId = 1L;
        } else {
            ultimoId = ultimoId + 1L;
        }
        _entity.setId(new CompositeKeyCentro(ultimoId, _entity.getUniversidad().getId()));
        EntityManager emUniversidad = universidadFacadeRest.getEntityManager();
        Universidad universidad = emUniversidad.find(Universidad.class, _entity.getUniversidad().getId());
        _entity.setUniversidad(universidad);
        super.create(_entity);
    }

    /**
     * Modifica un centro.
     *
     * @param _idCentro
     * @param _idUniversidad
     * @param _entity
     */
    @PUT
    @Path("{idCentro}/{idUniversidad}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("idCentro") Long _idCentro,
            @PathParam("idUniversidad") Long _idUniversidad,
            Centro _entity) {
        CompositeKeyCentro id = new CompositeKeyCentro(_idCentro, _idUniversidad);
        _entity.setId(id);
        super.edit(_entity);
    }

    /**
     * Elimina un centro.
     *
     * @param _idCentro
     * @param _idUniversidad
     */
    @DELETE
    @Path("{idCentro}/{idUniversidad}")
    public void remove(@PathParam("idCentro") Long _idCentro,
            @PathParam("idUniversidad") Long _idUniversidad) {
        CompositeKeyCentro id = new CompositeKeyCentro(_idCentro, _idUniversidad);
        super.remove(super.find(id));
    }

    /**
     * Encuentra un centro.
     *
     * @param _idCentro
     * @param _idUniversidad
     * @return
     */
    @GET
    @Path("/fnd/{idCentro}/{idUniversidad}")
    @Produces(MediaType.APPLICATION_JSON)
    public Centro find(@PathParam("idCentro") Long _idCentro,
            @PathParam("idUniversidad") Long _idUniversidad) {
        CompositeKeyCentro id = new CompositeKeyCentro(_idCentro, _idUniversidad);
        return super.find(id);
    }

    /**
     * Devuelve todos los centros.
     *
     * @return
     */
    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Centro> findAll() {
        return super.findAll();
    }

    /**
     * Devuelve todos los centros dentro de un rango.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Centro> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve la cuenta de centros.
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

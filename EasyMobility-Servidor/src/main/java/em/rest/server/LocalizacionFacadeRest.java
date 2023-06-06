/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: LocalizacionFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.bd.Localizacion;
import java.util.List;
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
@Path("location")
public class LocalizacionFacadeRest extends AbstractFacade<Localizacion> {

    @PersistenceContext(unitName = "EasyMobiliy-PU")
    private EntityManager em;

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
     * Construye un LocalizacionFacadeRest
     *
     */
    public LocalizacionFacadeRest() {
        super(Localizacion.class);
    }

    /**
     * Crea una localización.
     *
     * @param _entity
     */
    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Localizacion _entity) {
        super.create(_entity);
    }

    /**
     * Modifica una localización.
     *
     * @param _id
     * @param _entity
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Long _id, Localizacion _entity) {
        _entity.setId(_id);
        super.edit(_entity);
    }

    /**
     * Elimina una localización.
     *
     * @param _id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long _id) {
        super.remove(super.find(_id));
    }

    /**
     * Encuentra una localización.
     *
     * @param _id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Localizacion find(@PathParam("id") Long _id) {
        return super.find(_id);
    }

    /**
     * Devuelve todas las localizaciones.
     *
     * @return
     */
    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Localizacion> findAll() {
        return super.findAll();
    }

    /**
     * Devuelve localizaciones dentro de un rango.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Localizacion> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve el numero de localizaciones.
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

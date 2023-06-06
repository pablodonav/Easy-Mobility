/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: IdiomaFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.bd.Idioma;
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
@Path("language")
public class IdiomaFacadeRest extends AbstractFacade<Idioma> {

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
     * Construye un IdiomaFacadeRest
     *
     */
    public IdiomaFacadeRest() {
        super(Idioma.class);
    }

    /**
     * Crea un nuevo idioma.
     *
     * @param _entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void create(Idioma _entity) {
        super.create(_entity);
    }

    /**
     * Modifica un idioma.
     *
     * @param _id
     * @param _entity
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Long _id, Idioma _entity) {
        _entity.setId(_id);
        super.edit(_entity);
    }

    /**
     * Elimina un idioma.
     *
     * @param _numIdentificacion
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("numIdentificacion") String _numIdentificacion) {
        super.remove(super.find(_numIdentificacion));
    }

    /**
     * Encuentra un idioma.
     *
     * @param _id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Idioma find(@PathParam("id") Long _id) {
        return super.find(_id);
    }

    /**
     * Devuelve todos los idiomas.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Idioma> findAll() {
        return super.findAll();
    }

    /**
     * Devuelve idiomas dentro de un rango.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Idioma> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve la cuenta de idiomas.
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

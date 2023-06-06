/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: UniversidadFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.bd.Universidad;
import java.util.ArrayList;
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
@Path("university")
public class UniversidadFacadeRest extends AbstractFacade<Universidad> {

    @PersistenceContext(unitName = "EasyMobiliy-PU")
    private EntityManager em;

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
     * Construye un UniversidadFacadeRest
     *
     */
    public UniversidadFacadeRest() {
        super(Universidad.class);
    }

    /**
     * Crea una universidad.
     *
     * @param _entity
     */
    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Universidad _entity) {
        super.create(_entity);
    }

    /**
     * Modifica una universidad.
     *
     * @param _id
     * @param _entity
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Long _id, Universidad _entity) {
        _entity.setId(_id);
        super.edit(_entity);
    }

    /**
     * Elimina una universidad.
     *
     * @param _id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long _id) {
        super.remove(super.find(_id));
    }

    /**
     * Encuentra una universidad.
     *
     * @param _id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Universidad find(@PathParam("id") Long _id) {
        return super.find(_id);
    }

    /**
     * Devuelve todas las universidades.
     *
     * @return
     */
    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Universidad> findAll() {
        return super.findAll();
    }

    /**
     * Devuelve universidades dentro de un rango.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Universidad> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve la cuenta de universidades.
     *
     * @return
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * Devuelve las universidades de un pais concreto.
     *
     * @param _nombrePais
     * @return
     */
    @GET
    @Path("universidades-por-pais/{nombrePais}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Universidad> findByCountry(@PathParam("nombrePais") String _nombrePais) {
        List<Universidad> universidades = this.findAll();
        List<Universidad> universidadesPais = new ArrayList<Universidad>();

        for (Universidad universidad : universidades) {
            if (universidad.getLocalizacion().getPais().equals(_nombrePais)) {
                universidadesPais.add(universidad);
            }
        }

        return universidadesPais;
    }
}

/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CoordinadorFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.bd.Coordinador;
import em.bd.Usuario;
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
@Path("coordinator")
public class CoordinadorFacadeRest extends AbstractFacade<Coordinador> {

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
     * Construye un CoordinadorFacadeRest
     *
     */
    public CoordinadorFacadeRest() {
        super(Coordinador.class);
    }

    /**
     * Crea un nuevo coordinador.
     *
     * @param _entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void create(Coordinador _entity) {
        super.create(_entity);
    }

    /**
     * Modifica un coordinador.
     *
     * @param _numIdentificacion
     * @param _entity
     */
    @PUT
    @Path("{numIdentificacion}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("numIdentificacion") String _numIdentificacion, Coordinador _entity) {
        _entity.setNumIdentificacion(_numIdentificacion);
        super.edit(_entity);
    }

    /**
     * Elimina un coordinador.
     *
     * @param _numIdentificacion
     */
    @DELETE
    @Path("{numIdentificacion}")
    public void remove(@PathParam("numIdentificacion") String _numIdentificacion) {
        super.remove(super.find(_numIdentificacion));
    }

    /**
     * Encuentra un coordinador.
     *
     * @param _numIdentificacion
     * @return
     */
    @GET
    @Path("{numIdentificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario find(@PathParam("numIdentificacion") String _numIdentificacion) {
        return super.find(_numIdentificacion);
    }

    /**
     * Encuentra coordinadores por su correo.
     *
     * @param _email
     * @return
     */
    @GET
    @Path("email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Coordinador findByEmail(@PathParam("email") String _email) {
        List<Coordinador> coordinadores = super.findAll();
        for (Coordinador coordinador : coordinadores) {
            if (coordinador.getCorreo() == null ? _email == null : coordinador.getCorreo().equals(_email)) {
                return coordinador;
            }
        }
        return null;
    }

    /**
     * Devuelve todos los coordinadores.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Coordinador> findAll() {
        return super.findAll();
    }

    /**
     * Devuelve los coordinadores dentro de un rango.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Coordinador> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve la cuenta de coordinadores.
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

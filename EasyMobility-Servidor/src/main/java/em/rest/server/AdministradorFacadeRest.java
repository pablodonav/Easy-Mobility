/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AdministradorFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.bd.Administrador;
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
@Path("administrator")
public class AdministradorFacadeRest extends AbstractFacade<Administrador> {

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
     * Construye un AdministradorFacadeRest
     *
     */
    public AdministradorFacadeRest() {
        super(Administrador.class);
    }

    /**
     * Crea un administrador.
     *
     * @param _entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void create(Administrador _entity) {
        super.create(_entity);
    }

    /**
     * Modifica un administrador.
     *
     * @param _numIdentificacion
     * @param _entity
     */
    @PUT
    @Path("{numIdentificacion}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("numIdentificacion") String _numIdentificacion, Administrador _entity) {
        _entity.setNumIdentificacion(_numIdentificacion);
        super.edit(_entity);
    }

    /**
     * Elimina un administrador.
     *
     * @param _numIdentificacion
     */
    @DELETE
    @Path("{numIdentificacion}")
    public void remove(@PathParam("numIdentificacion") String _numIdentificacion) {
        super.remove(super.find(_numIdentificacion));
    }

    /**
     * Encuentra un administrador.
     *
     * @param _numIdentificacion
     * @return
     */
    @GET
    @Path("{numIdentificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Administrador find(@PathParam("numIdentificacion") String _numIdentificacion) {
        return super.find(_numIdentificacion);
    }

    /**
     * Encuentra todos los administradores.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Administrador> findAll() {
        return super.findAll();
    }

    /**
     * Encuentra administradores en un rango determinado.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Administrador> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve la cuenta de administradores.
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

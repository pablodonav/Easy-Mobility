/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AlumnoFacadeRest.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.rest.server;

import em.bd.Alumno;
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
@Path("student")
public class AlumnoFacadeRest extends AbstractFacade<Alumno> {

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
     * Construye un AlumnoFacadeRest
     *
     */
    public AlumnoFacadeRest() {
        super(Alumno.class);
    }

    /**
     * Crea un alumno.
     *
     * @param entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void create(Alumno entity) {
        super.create(entity);
    }

    /**
     * Modifica un alumno.
     *
     * @param _numIdentificacion
     * @param _entity
     */
    @PUT
    @Path("{numIdentificacion}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("numIdentificacion") String _numIdentificacion, Alumno _entity) {
        _entity.setNumIdentificacion(_numIdentificacion);
        super.edit(_entity);
        System.out.println("SERVER ALUMNO RECEIVED --> " + _entity.toJson());
    }

    /**
     * Elimina un alumno.
     *
     * @param _numIdentificacion
     */
    @DELETE
    @Path("{numIdentificacion}")
    public void remove(@PathParam("numIdentificacion") String _numIdentificacion) {
        super.remove(super.find(_numIdentificacion));
    }

    /**
     * Encuentra un alumno.
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
     * Encuentra alumno por su email.
     *
     * @param _email
     * @return
     */
    @GET
    @Path("email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario findByEmail(@PathParam("email") String _email) {
        List<Alumno> alumnos = super.findAll();
        for (Alumno alumno : alumnos) {
            if (alumno.getCorreo() == null ? _email == null : alumno.getCorreo().equals(_email)) {
                return alumno;
            }
        }
        return null;
    }

    /**
     * Encuentra todos los alumnos.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Alumno> findAll() {
        return super.findAll();
    }

    /**
     * Devuelve alumnos dentro de un rango determinado.
     *
     * @param _from
     * @param _to
     * @return
     */
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alumno> findRange(@PathParam("from") Integer _from, @PathParam("to") Integer _to) {
        return super.findRange(new int[]{_from, _to});
    }

    /**
     * Devuelve la cuenta de alumnos.
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

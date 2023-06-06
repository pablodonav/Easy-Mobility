/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Arrancador.java
    Date: 24 may. 2023
  
    Authors: Pablo Doñate
 */
package em.ejb;

import em.bd.Administrador;
import em.common.enums.TipoIdentificacion;
import em.dummy.Hasher;
import em.rest.server.AdministradorFacadeRest;
import javax.ejb.EJB;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "Arrancador", urlPatterns = "/arrancador", loadOnStartup = 1)
public class Arrancador extends HttpServlet {

    @EJB
    private AdministradorFacadeRest adminFacadeRest;
    
    private final Config config = ConfigProvider.getConfig();

    /**
     * Método que se ejecuta al arrancar el servidor. Crea el admin si no está creado.
     * 
     */
    @Override
    public void init() {
        System.out.println("==== EeasyMobility  -> Arrancando... =====");
        if(Integer.parseInt(adminFacadeRest.countREST()) == 0) { // No existe admin.

            String numIdentificacion = this.config.getValue("em.adminIdentification", String.class);
            String password = this.config.getValue("em.adminDefaultPassword", String.class);
            String telefono = this.config.getValue("em.adminTelefono", String.class);
            String nombre = this.config.getValue("em.adminNombre", String.class);
            String apellido1 = this.config.getValue("em.adminApellido1", String.class);
            String apellido2 = this.config.getValue("em.adminApellido2", String.class);
            String correo = this.config.getValue("em.adminCorreo", String.class);

            Administrador admin = new Administrador(numIdentificacion, TipoIdentificacion.DNI,
                    Integer.valueOf(telefono), nombre, apellido1, apellido2, correo, password, 
                    false);
            adminFacadeRest.create(admin);
        }
    }
}

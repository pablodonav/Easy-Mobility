/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: SuccessMessages.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.enums;

public enum SuccessMessages {
    /* Mensajes éxito Alumno */
    EXITO_ADD_ALUMNO("Alumno añadido con éxito"),
    EXITO_DELETE_ALUMNO("Alumno eliminado con éxito"),
    EXITO_EDIT_ALUMNO("Alumno editado con éxito"),
    EXITO_EDIT_PASSWORD("Contraseña editada con éxito"),
    EXITO_ADD_FILE("El fichero se ha cargado con éxito"),
    EXITO_REMOVE_FILE("El fichero se ha borrado exitosamente."),
    EXITO_LOGOUT("Se ha cerrado sesión correctamente."),
    /* Mensajes éxito reset password */
    EXITO_RESET_PASSWORD("La contraseña se ha reseteado. Por favor, revisa tu buzón de correo."),
    /* Mensajes éxito Alumno */
    EXITO_ADD_IDIOMA("Idioma añadido con éxito"),
    /* Mensajes éxito Fichero */
    EXITO_DELETE_FICHERO("Fichero eliminado con éxito");

    private final String mensajeExito;

    /**
     * Construye el mensaje de éxito.
     *
     * @param _mensajeExito
     */
    SuccessMessages(String _mensajeExito) {
        this.mensajeExito = _mensajeExito;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override

    public String toString() {
        return mensajeExito;
    }
}

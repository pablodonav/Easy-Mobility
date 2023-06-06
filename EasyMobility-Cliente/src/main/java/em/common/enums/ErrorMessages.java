/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: ErrorMessages.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.enums;

public enum ErrorMessages {
    ERROR_CONEXION_CON_SERVIDOR("¡Error en la conexión con el servidor!"),
    
    /* Mensajes Error Usuario */
    ERROR_SESION_CADUCADA("¡Sesión inválida!"),
    
    /* Comienzo códigos de error http */
    STATUS_NOK_GROUP_400("4"),
    STATUS_NOK_GROUP_500("5"),
    
    /* Mensajes Error Login */
    ERROR_DATOS_INTRODUCIDOS("¡El usuario con este identificador no existe o el tipo es incorrecto!"),
    ERROR_CAMPOS_INVALIDOS("¡Campos inválidos! Vuelva a introducir los datos correctamente"),
    ERROR_CONTRASENYA("¡Contraseña incorrecta!"),
    
    /* Mensajes Error Alumno */
    ERROR_OBTENER_ALUMNO("¡No se ha podido obtener el alumno seleccionado!"),
    ERROR_OBTENER_ALUMNOS("¡No se ha podido obtener la lista con alumnos!"),
    ERROR_ALUMNO_YA_EXISTE("¡No se ha podido realizar la inserción. Ya existe un alumno con dicho número de identificación!"),
    ERROR_PWD_DISTINTOS("¡No se ha podido realizar la inserción. Las contraseñas insertadas no son iguales!"),
    ERROR_ADD_ALUMNO("¡No se ha podido realizar la inserción del alumno!"),
    ERROR_EDIT_ALUMNO("¡No se ha podido realizar la edición del alumno!"),
    ERROR_EDIT_COORDINADOR("¡No se ha podido realizar la edición del coordinador!"),
    ERROR_DELETE_ALUMNO("¡No se ha podido realizar el borrado del alumno!"),
    
    /* Mensajes Error Coordinador */
    ERROR_OBTENER_COORDINADOR("¡No se ha podido obtener el coordinador seleccionado!"),
    ERROR_OBTENER_COORDINADORES("¡No se ha podido obtener la lista con coordinadores!"),
   
    /* Mensajes Error Estudio */
    ERROR_OBTENER_ESTUDIO("¡No se ha podido obtener el estudio seleccionado!"),
    ERROR_OBTENER_ESTUDIOS("¡No se ha podido obtener la lista con estudios disponibles!"),
    
    /* Mensajes Error Acuerdo */
    ERROR_OBTENER_ACUERDOS("¡No se han podido obtener los acuerdos de un estudio!"),
    ERROR_OBTENER_ACUERDO_ALUMNO("¡No se ha podido obtener el acuerdo de un estudiante!"),
    ERROR_ADD_ACUERDO("¡No se ha podido crear el acuerdo!"),
    ERROR_DELETE_ACUERDO("¡No se ha podido eliminar el acuerdo!"),
    ERROR_EDIT_ACUERDO("¡No se ha podido editar el acuerdo!"),
    
    /* Mensajes Error Ficheros */
    ERROR_OBTENER_FICHEROS("¡No existen ficheros de este alumno!"),
    ERROR_BORRAR_FICHERO("¡No se ha podido eliminar el fichero !"),
    
    /* Mensajes Error Centros */
    ERROR_OBTENER_CENTROS("¡No se han podido obtener los centros existentes!"),
    
    /* Mensajes Error Localizaciones */
    ERROR_OBTENER_PAISES("¡No se han podido obtener los paises existentes!"),
    
    /* Mensajes Error Universidades */
    ERROR_OBTENER_UNIVERSIDADES("¡No se han podido obtener las universidades existentes!"),
    
    /* Mensajes Error Idiomas */
    ERROR_OBTENER_IDIOMA("¡No se han podido obtener el idioma requerido!"),
    ERROR_OBTENER_IDIOMAS("¡No se han podido obtener los idiomas existentes!"),
    ERROR_ADD_IDIOMA("¡No se ha podido realizar la inserción del idioma!"),
    
    /*Mensajes Error Editar Perfil */
    ERROR_PASSWORDS_DIFERENTES("¡Las nuevas contraseñas deben de ser iguales!"),
    ERROR_PASSWORD_ORIGINAL_INCORRECTA("¡La contraseña actual es incorrecta!"),
    
    /* Mensajes Error Resetear Password */
    ERROR_RESET_EMAIL_NOT_FOUND("El correo electrónico introducido no está dado de alta."),
    ERROR_SENDING_EMAIL("El correo no se ha podido enviar. Por favor, intentelo de nuevo mas tarde"),
    
    /* Mensajes Aviso */
    WARNING_CAMBIO_PWD("¡No se ha realizado el cambio de la contraseña por defecto.! Por seguridad le aconsejamos cambiarla lo antes posible."),
    
    /* Mensaje Error Carga Fichero */
    ERROR_CARGA_FICHERO("El archivo no se ha podido cargar en el sistema. Inténtelo de nuevo mas tarde.");

    private final String mensajeError;

    /**
     * Construye el mensaje de error.
     *
     * @param _mensajeError
     */
    ErrorMessages(String _mensajeError) {
        this.mensajeError = _mensajeError;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return mensajeError;
    }
}

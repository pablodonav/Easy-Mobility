/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: UsuarioDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import com.google.gson.Gson;
import em.common.enums.TipoIdentificacion;
import java.io.Serializable;

public class UsuarioDTO implements Serializable {

    private String numIdentificacion = "";
    private TipoIdentificacion documentoIdentificacion = TipoIdentificacion.DNI;
    private Integer telefono = 0; // Número formado por Prefijo + Número
    private String nombre = "";
    private String apellido1 = "";
    private String apellido2 = "";
    private String correo = "";
    private String hashContrasenya = "";
    private boolean haCambiadoPwd = false;

    /**
     * Construye un usuario con parámetros.
     *
     * @param _numIdentificacion
     * @param _documentoIdentificacion
     * @param _telefono
     * @param _nombre
     * @param _apellido1
     * @param _apellido2
     * @param _correo
     * @param _hashContrasenya
     * @param _haCambiadoPwd
     */
    public UsuarioDTO(String _numIdentificacion, TipoIdentificacion _documentoIdentificacion, Integer _telefono, 
            String _nombre, String _apellido1, String _apellido2, String _correo, String _hashContrasenya, 
            boolean _haCambiadoPwd) {
        this.numIdentificacion = _numIdentificacion;
        this.documentoIdentificacion = _documentoIdentificacion;
        this.telefono = _telefono;
        this.nombre = _nombre;
        this.apellido1 = _apellido1;
        this.apellido2 = _apellido2;
        this.correo = _correo;
        this.hashContrasenya = _hashContrasenya;
        this.haCambiadoPwd = _haCambiadoPwd;
    }

    /**
     * Construye un usuario sin parámetros.
     *
     */
    public UsuarioDTO() {

    }

    /**
     * Devuelve el número de identificación del usuario.
     *
     * @return
     */
    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    /**
     * Establece el número de identificación del usuario.
     *
     * @param _numIdentificacion
     */
    public void setNumIdentificacion(String _numIdentificacion) {
        this.numIdentificacion = _numIdentificacion;
    }

    /**
     * Devuelve el documento de identificación del usuario.
     *
     * @return
     */
    public TipoIdentificacion getDocumentoIdentificacion() {
        return documentoIdentificacion;
    }

    /**
     * Establece el documento de identificación del usuario.
     *
     * @param _documentoIdentificacion
     */
    public void setDocumentoIdentificacion(TipoIdentificacion _documentoIdentificacion) {
        this.documentoIdentificacion = _documentoIdentificacion;
    }

    /**
     * Devuelve el teléfono del usuario.
     *
     * @return
     */
    public Integer getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del usuario.
     *
     * @param _telefono
     */
    public void setTelefono(Integer _telefono) {
        this.telefono = _telefono;
    }

    /**
     * Devuelve el nombre del usuario.
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param _nombre
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    /**
     * Devuelve el primer apellido del usuario.
     *
     * @return
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Establece el primer apellido del usuario.
     *
     * @param _apellido1
     */
    public void setApellido1(String _apellido1) {
        this.apellido1 = _apellido1;
    }

    /**
     * Devuelve el segundo apellido del usuario.
     *
     * @return
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Establece el segundo apellido del usuario.
     *
     * @param _apellido2
     */
    public void setApellido2(String _apellido2) {
        this.apellido2 = _apellido2;
    }

    /**
     * Devuelve el correo electrónico del usuario.
     *
     * @return
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param _correo
     */
    public void setCorreo(String _correo) {
        this.correo = _correo;
    }

    /**
     * Devuelve la contraseña del usuario.
     *
     * @return
     */
    public String getHashContrasenya() {
        return hashContrasenya;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param _hashContrasenya
     */
    public void setHashContrasenya(String _hashContrasenya) {
        this.hashContrasenya = _hashContrasenya;
    }

    /**
     * Devuelve cierto si el usuario ha cambiado su password. En caso contrario,
     * devovlerá falso.
     *
     * @return
     */
    public boolean isHaCambiadoPwd() {
        return haCambiadoPwd;
    }

    /**
     * Establece el estado de cambio de password.
     *
     * @param _haCambiadoPwd
     */
    public void setHaCambiadoPwd(boolean _haCambiadoPwd) {
        this.haCambiadoPwd = _haCambiadoPwd;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "UsuarioDTO{" + "numIdentificacion=" + numIdentificacion
                + ", documentoIdentificacion=" + documentoIdentificacion
                + ", telefono=" + telefono + ", nombre=" + nombre
                + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
                + ", correo=" + correo + ", hashContrasenya=" + hashContrasenya
                + '}';
    }

    /**
     * Sobreescribe método hashCode().
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (telefono != null ? telefono.hashCode() : 0);
        return hash;
    }

    /**
     * Sobreescribe método equals().
     *
     * @param _object
     * @return
     */
    @Override
    public boolean equals(Object _object) {
        if (!(_object instanceof UsuarioDTO)) {
            return false;
        }
        UsuarioDTO other = (UsuarioDTO) _object;
        return !((this.numIdentificacion == null
                && other.numIdentificacion != null)
                || (this.numIdentificacion != null
                && !this.numIdentificacion.equals(other.numIdentificacion)));
    }

    /**
     * Devuelve al usuario en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

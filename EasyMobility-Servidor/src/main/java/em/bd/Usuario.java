/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Usuario.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd;

import com.google.gson.Gson;
import em.common.enums.TipoIdentificacion;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Usuario implements Serializable {

    @Id
    @Column(name = "NUM_IDENTIFICACION")
    private String numIdentificacion;

    @Column(name = "TIPO_IDENTIFICACION")
    private TipoIdentificacion documentoIdentificacion;
    @Column(name = "TELEFONO")
    private Integer telefono; // Número formado por Prefijo + Número
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO1")
    private String apellido1;
    @Column(name = "APELLIDO2")
    private String apellido2;
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "PWD")
    private String hashContrasenya;
    @Column(name = "HAS_PWD_CHANGED")
    private boolean haCambiadoPwd;

    /**
     * Construye un usuario sin parámetros.
     * 
     */
    public Usuario() {
    }

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
    public Usuario(String _numIdentificacion, TipoIdentificacion _documentoIdentificacion,
            Integer _telefono, String _nombre, String _apellido1, String _apellido2,
            String _correo, String _hashContrasenya, boolean _haCambiadoPwd) {
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
     * Devuelve el identificador del usuario.
     * 
     * @return 
     */
    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    /**
     * Establece el identificador del usuario.
     * 
     * @param _numIdentificacion 
     */
    public void setNumIdentificacion(String _numIdentificacion) {
        this.numIdentificacion = _numIdentificacion;
    }

    /**
     * Devuelve el tipo de identificacion del usuario.
     * 
     * @return 
     */
    public TipoIdentificacion getDocumentoIdentificacion() {
        return documentoIdentificacion;
    }

    /**
     * Establece el tipo de identificacion del usuario.
     * 
     * @param _documentoIdentificacion 
     */
    public void setDocumentoIdentificacion(TipoIdentificacion _documentoIdentificacion) {
        this.documentoIdentificacion = _documentoIdentificacion;
    }

    /**
     * Devuelve el telefono del usuario.
     * 
     * @return 
     */
    public Integer getTelefono() {
        return telefono;
    }

    /**
     * Establece el telefono del usuario.
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
     * Devuelve el apellido 1 del usuario.
     * 
     * @return 
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Establece el apellido 1 del usuario.
     * 
     * @param _apellido1 
     */
    public void setApellido1(String _apellido1) {
        this.apellido1 = _apellido1;
    }

    /**
     * Devuelve el apellido 2 del usuario.
     * 
     * @return 
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Establece el apellido 2 del usuario.
     * 
     * @param _apellido2 
     */
    public void setApellido2(String _apellido2) {
        this.apellido2 = _apellido2;
    }

    /**
     * Devuelve el correo del usuario.
     * 
     * @return 
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo del usuario.
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
     * Devuelve el estado del cambio de contraseña del usuario.
     * 
     * @return 
     */
    public boolean isHaCambiadoPwd() {
        return haCambiadoPwd;
    }

    /**
     * Establece el estado del cambio de contraseña del usuario.
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
        return "Usuario{" + "numIdentificacion=" + numIdentificacion
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
        if (!(_object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) _object;
        return !((this.numIdentificacion == null
                && other.numIdentificacion != null)
                || (this.numIdentificacion != null
                && !this.numIdentificacion.equals(other.numIdentificacion)));
    }

    /**
     * Devuelve el usuario en formato JSON.
     * 
     * @return 
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

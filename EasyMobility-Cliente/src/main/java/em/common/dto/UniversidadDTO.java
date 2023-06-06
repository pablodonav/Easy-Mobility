/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: UniversidadDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import com.google.gson.Gson;
import java.io.Serializable;

public class UniversidadDTO implements Serializable {

    private Long id = 0L;
    private String nombre = "";
    private LocalizacionDTO localizacion = new LocalizacionDTO();

    /**
     * Construye una universidad con parámetros.
     *
     * @param _id
     * @param _nombre
     * @param _localizacion
     */
    public UniversidadDTO(Long _id, String _nombre, LocalizacionDTO _localizacion) {
        this.id = _id;
        this.nombre = _nombre;
        this.localizacion = _localizacion;
    }
    
    /**
     * Construye una universidad sin parámetros.
     * 
     */
    public UniversidadDTO() {
    }

    /**
     * Devuelve el id de la universidad.
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id de la universidad.
     *
     * @param _id
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve el nombre de la universidad.
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la universidad.
     *
     * @param _nombre
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    /**
     * Devuelve la localización de la universidad.
     *
     * @return
     */
    public LocalizacionDTO getLocalizacion() {
        return localizacion;
    }

    /**
     * Establece la localización de la universidad.
     *
     * @param _localizacion
     */
    public void setLocalizacion(LocalizacionDTO _localizacion) {
        this.localizacion = _localizacion;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "UniversidadDTO{" + "id=" + id + ", nombre=" + nombre + '}';
    }

    /**
     * Sobreescribe método hashCode().
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        if (!(_object instanceof UniversidadDTO)) {
            return false;
        }
        UniversidadDTO other = (UniversidadDTO) _object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * Devuelve la universidad en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

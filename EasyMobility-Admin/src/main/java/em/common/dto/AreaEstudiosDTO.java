/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AreaEstudiosDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import com.google.gson.Gson;
import java.io.Serializable;

public class AreaEstudiosDTO implements Serializable {

    private Long id = 0L;
    private String nombre = "";

    /**
     * Construye un area de estudios con parámetros.
     *
     * @param _id
     * @param _nombre
     */
    public AreaEstudiosDTO(Long _id, String _nombre) {
        this.id = _id;
        this.nombre = _nombre;
    }

    /**
     * Construye un area de estudios sin parámetros.
     *
     */
    public AreaEstudiosDTO() {
    }

    /**
     * Devuelve el identificador del area de estudios.
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del area de estudios.
     *
     * @param _id
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve el nombre del area de estudios.
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del area de estudios.
     *
     * @param _nombre
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "AreaEstudios{" + "id=" + id + ", nombre=" + nombre + '}';
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(_object instanceof AreaEstudiosDTO)) {
            return false;
        }
        AreaEstudiosDTO other = (AreaEstudiosDTO) _object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * Devuelve el area de estudios en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

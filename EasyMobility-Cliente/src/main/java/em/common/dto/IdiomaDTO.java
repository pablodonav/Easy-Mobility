/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: IdiomaDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import com.google.gson.Gson;
import java.io.Serializable;

public class IdiomaDTO implements Serializable {

    private Long id = 0L;
    private String nombre = "";

    /**
     * Construye un idioma con parámetros.
     *
     * @param _id
     * @param _nombre
     */
    public IdiomaDTO(Long _id, String _nombre) {
        this.id = _id;
        this.nombre = _nombre;
    }
    
    /**
     * Construye un idioma sin parámetros.
     * 
     */
    public IdiomaDTO() {
    }

    /**
     * Devuelve el identificador del idioma.
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del idioma.
     *
     * @param _id
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve el nombre del idioma.
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del idioma.
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
        return "IdiomaDTO{" + "id=" + id + ", nombre=" + nombre + '}';
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
        if (!(_object instanceof IdiomaDTO)) {
            return false;
        }
        IdiomaDTO other = (IdiomaDTO) _object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Devuelve el idioma en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

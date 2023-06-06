/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: LocalizacionDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import com.google.gson.Gson;
import java.io.Serializable;

public class LocalizacionDTO implements Serializable {

    private Long id = 0L;
    private String ciudad = "";
    private String pais = "";
    private String direccion = "";

    /**
     * Construye una localización con parámetros.
     *
     * @param _id
     * @param _ciudad
     * @param _pais
     * @param _direccion
     */
    public LocalizacionDTO(Long _id, String _ciudad, String _pais, String _direccion) {
        this.id = _id;
        this.ciudad = _ciudad;
        this.pais = _pais;
        this.direccion = _direccion;
    }

    /**
     * Construye una localización sin parámetros.
     *
     */
    public LocalizacionDTO() {
    }

    /**
     * Devuelve la ciudad de la localización.
     *
     * @return
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad de la localización.
     *
     * @param _ciudad
     */
    public void setCiudad(String _ciudad) {
        this.ciudad = _ciudad;
    }

    /**
     * Devuelve el país de la localización.
     *
     * @return
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el país de la localización.
     *
     * @param _pais
     */
    public void setPais(String _pais) {
        this.pais = _pais;
    }

    /**
     * Devuelve la dirección de la localización.
     *
     * @return
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la localización.
     *
     * @param _direccion
     */
    public void setDireccion(String _direccion) {
        this.direccion = _direccion;
    }

    /**
     * Devuelve el id de la localización.
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id de la localización.
     *
     * @param _id
     */
    public void setId(Long _id) {
        this.id = _id;
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
        if (!(_object instanceof LocalizacionDTO)) {
            return false;
        }
        LocalizacionDTO other = (LocalizacionDTO) _object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "Localizacion{" + "id=" + id + ", ciudad=" + ciudad + ", pais=" + pais + ", direccion=" + direccion + '}';
    }

    /**
     * Devuelve la localización en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

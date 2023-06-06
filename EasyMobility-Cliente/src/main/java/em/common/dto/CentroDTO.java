/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CentroDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import com.google.gson.Gson;
import em.bd.compositekeys.CompositeKeyCentro;
import java.io.Serializable;

public class CentroDTO implements Serializable {

    private CompositeKeyCentro id = new CompositeKeyCentro();
    private LocalizacionDTO localizacion = new LocalizacionDTO();
    private UniversidadDTO universidad = new UniversidadDTO();
    private String nombre = "";

    /**
     * Construye un centro con parámetros.
     * 
     * @param _idCentro
     * @param _nombre
     * @param _universidad
     * @param _localizacion 
     */
    public CentroDTO(Long _idCentro, String _nombre, UniversidadDTO _universidad, LocalizacionDTO _localizacion) {
        this.id = new CompositeKeyCentro(_idCentro, _universidad.getId());
        this.nombre = _nombre;
        this.universidad = _universidad;
        this.localizacion = _localizacion;
    }
    
    /**
     * Construye un centro sin parámetros.
     * 
     */
    public CentroDTO() {
    }

    /**
     * Devuelve el id del centro.
     *
     * @return
     */
    public CompositeKeyCentro getId() {
        return id;
    }

    /**
     * Establece el id del centro.
     *
     * @param _id
     */
    public void setId(CompositeKeyCentro _id) {
        this.id = _id;
    }

    /**
     * Devuelve la universidad del centro.
     *
     * @return
     */
    public UniversidadDTO getUniversidad() {
        return universidad;
    }

    /**
     * Establece la universidad del centro.
     *
     * @param _universidad
     */
    public void setUniversidad(UniversidadDTO _universidad) {
        this.universidad = _universidad;
    }

    /**
     * Devuelve el nombre del centro.
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del centro.
     *
     * @param _nombre
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    /**
     * Devuelve la localización del centro.
     *
     * @return
     */
    public LocalizacionDTO getLocalizacion() {
        return localizacion;
    }

    /**
     * Establece la localización del centro.
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
        return "CentroDTO{" + "id=" + id.getIdCentro() + ", nombre=" + nombre + 
                ", idUniversidad=" + id.getIdUniversidad() + 
                ", localizacion=" + localizacion + '}';
    }

    /**
     * Sobreescribe método hashCode().
     *
     * @return
     */
    @Override
    public int hashCode() {
        Long id = Long.parseLong(this.id.getIdCentro() + "" + this.id.getIdUniversidad());

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
        Long id = Long.valueOf(this.id.getIdCentro() + "" + this.id.getIdUniversidad());

        if (!(_object instanceof CentroDTO)) {
            return false;
        }
        CentroDTO other = (CentroDTO) _object;
        Long idOther = Long.valueOf(other.id.getIdCentro() + "" + other.id.getIdUniversidad());
        return !((id == null && idOther != null) || (id != null && !id.equals(idOther)));
    }

    /**
     * Devuelve el centro en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

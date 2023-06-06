/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CompositeKeyCentro.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd.compositekeys;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Objects;

public class CompositeKeyCentro implements Serializable {

    // Identificadores del centro y de su universidad asociada.
    private Long idCentro = 0L;
    private Long idUniversidad = 0L;

    /**
     * Construye una clave compuesta de Centro con parámetros.
     *
     * @param _idCentro
     * @param _idUniversidad
     */
    public CompositeKeyCentro(Long _idCentro, Long _idUniversidad) {
        this.idCentro = _idCentro;
        this.idUniversidad = _idUniversidad;
    }

    /**
     * Construye una clave compuesta de Centro sin parámetros.
     *
     */
    public CompositeKeyCentro() {
    }

    /**
     * Devuelve el identificador del centro.
     *
     * @return
     */
    public Long getIdCentro() {
        return idCentro;
    }

    /**
     * Establece el identificador del centro.
     *
     * @param _idCentro
     */
    public void setIdCentro(Long _idCentro) {
        this.idCentro = _idCentro;
    }

    /**
     * Devuelve el identificador de la universidad.
     *
     * @return
     */
    public Long getIdUniversidad() {
        return idUniversidad;
    }

    /**
     * Establece el identificador de la universidad.
     *
     * @param _idUniversidad
     */
    public void setIdUniversidad(Long _idUniversidad) {
        this.idUniversidad = _idUniversidad;
    }

    /**
     * Sobreescribe método equals().
     *
     * @param _obj
     * @return
     */
    @Override
    public boolean equals(Object _obj) {
        if (!(_obj instanceof CompositeKeyCentro)) {
            return false;
        }
        CompositeKeyCentro comp = (CompositeKeyCentro) _obj;
        return !((!Objects.equals(comp.getIdCentro(), this.idCentro))
                || (!Objects.equals(comp.getIdUniversidad(), this.idUniversidad)));
    }

    /**
     * Sobreescribe método hashCode().
     *
     * @return
     */
    @Override
    public int hashCode() {
        Long id = this.idCentro + this.idUniversidad;
        return id.hashCode();
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "CompositeKeyCentro{" + "idCentro=" + idCentro + ", idUniversidad=" + idUniversidad + '}';
    }

    /**
     * Devuelve la clave compuesta en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

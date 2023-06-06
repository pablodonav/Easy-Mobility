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

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompositeKeyCentro implements Serializable {

    @Column(name = "CE_PK_ID")
    private Long idCentro;
    private Long idUniversidad;

    /**
     * Construye una clave compuesta de centro sin parámetros.
     * 
     */
    public CompositeKeyCentro() {
    }

    /**
     * Construye una clave compuesta de centro con parámetros.
     * 
     * @param _idCentro
     * @param _idUniversidad 
     */
    public CompositeKeyCentro(Long _idCentro, Long _idUniversidad) {
        this.idCentro = _idCentro;
        this.idUniversidad = _idUniversidad;
    }

    /**
     * Devuelve el id del centro.
     * 
     * @return 
     */
    public Long getIdCentro() {
        return idCentro;
    }

    /**
     * Establece el id del centro.
     * 
     * @param _idCentro 
     */
    public void setIdCentro(Long _idCentro) {
        this.idCentro = _idCentro;
    }

    /**
     * Devuelve la universidad de la clave.
     * 
     * @return 
     */
    public Long getIdUniversidad() {
        return idUniversidad;
    }

    /**
     * Establece la universidad de la clave.
     * 
     * @param _idUniversidad 
     */
    public void setIdUniversidad(Long _idUniversidad) {
        this.idUniversidad = _idUniversidad;
    }

    /**
     * Sobreescribe método hashCode().
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.idCentro);
        hash = 59 * hash + Objects.hashCode(this.idUniversidad);
        return hash;
    }

    /**
     * Sobreescribe método equals().
     * 
     * @param _obj
     * @return 
     */
    @Override
    public boolean equals(Object _obj) {
        if (this == _obj) {
            return true;
        }
        if (_obj == null) {
            return false;
        }
        if (getClass() != _obj.getClass()) {
            return false;
        }
        final CompositeKeyCentro other = (CompositeKeyCentro) _obj;
        if (!Objects.equals(this.idCentro, other.idCentro)) {
            return false;
        }
        return Objects.equals(this.idUniversidad, other.idUniversidad);
    }

}

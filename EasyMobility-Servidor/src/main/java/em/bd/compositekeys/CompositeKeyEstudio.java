/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: CompositeKeyEstudio.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd.compositekeys;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompositeKeyEstudio implements Serializable {

    @Column(name = "ES_PK_ID")
    private Long idEstudio;

    private CompositeKeyCentro idCentro;

    /**
     * Construye una clave compuesta de estudio.
     * 
     */
    public CompositeKeyEstudio() {
    }

    /**
     * Construye una clave compuesta de estudio con parámetros.
     * 
     * @param _idEstudio
     * @param _idCentro 
     */
    public CompositeKeyEstudio(Long _idEstudio, CompositeKeyCentro _idCentro) {
        this.idEstudio = _idEstudio;
        this.idCentro = _idCentro;
    }

    /**
     * Devuelve el id del estudio.
     * 
     * @return 
     */
    public Long getIdEstudio() {
        return idEstudio;
    }

    /**
     * Establece el id del estudio.
     * 
     * @param _idEstudio 
     */
    public void setIdEstudio(Long _idEstudio) {
        this.idEstudio = _idEstudio;
    }

    /**
     * Devuelve el id del centro.
     * 
     * @return 
     */
    public CompositeKeyCentro getIdCentro() {
        return idCentro;
    }

    /**
     * Establece el id del centro.
     * 
     * @param _idCentro 
     */
    public void setIdCentro(CompositeKeyCentro _idCentro) {
        this.idCentro = _idCentro;
    }

    /**
     * Sobreescribe método hashCode().
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idEstudio);
        hash = 41 * hash + Objects.hashCode(this.idCentro);
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
        final CompositeKeyEstudio other = (CompositeKeyEstudio) _obj;
        if (!Objects.equals(this.idEstudio, other.idEstudio)) {
            return false;
        }
        return Objects.equals(this.idCentro, other.idCentro);
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

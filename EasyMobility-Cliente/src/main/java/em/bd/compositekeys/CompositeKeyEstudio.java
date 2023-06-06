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

public class CompositeKeyEstudio implements Serializable {

    private Long idEstudio;

    private CompositeKeyCentro idCentro;

    /**
     * Construye un CompositeKeyEstudio sin parámetros.
     *
     */
    public CompositeKeyEstudio() {
    }

    /**
     * Construye un CompositeKeyEstudio con parámetros.
     *
     * @param _idEstudio
     * @param _idCentro
     */
    public CompositeKeyEstudio(Long _idEstudio, CompositeKeyCentro _idCentro) {
        this.idEstudio = _idEstudio;
        this.idCentro = _idCentro;
    }

    /**
     * Devuelve el CompositeKey asociado al centro.
     *
     * @return
     */
    public CompositeKeyCentro getIdCentro() {
        return idCentro;
    }

    /**
     * Establece el CompositeKey asociado al centro.
     *
     * @param _idCentro
     */
    public void setIdCentro(CompositeKeyCentro _idCentro) {
        this.idCentro = _idCentro;
    }

    /**
     * Devuelve el identificador del estudio.
     *
     * @return
     */
    public Long getIdEstudio() {
        return idEstudio;
    }

    /**
     * Establece el identificador del estudio.
     *
     * @param _idEstudio
     */
    public void setIdEstudio(Long _idEstudio) {
        this.idEstudio = _idEstudio;
    }

    /**
     * Sobreescribe método equals().
     *
     * @param _obj
     * @return
     */
    @Override
    public boolean equals(Object _obj) {
        if (!(_obj instanceof CompositeKeyEstudio)) {
            return false;
        }
        CompositeKeyEstudio comp = (CompositeKeyEstudio) _obj;
        return !((!Objects.equals(comp.getIdEstudio(), this.idEstudio))
                || (!Objects.equals(comp.getIdCentro().getIdCentro(), this.idCentro.getIdCentro()))
                || (!Objects.equals(comp.getIdCentro().getIdUniversidad(), this.idCentro.getIdUniversidad())));
    }

    /**
     * Sobreescribe método hashCode().
     *
     * @return
     */
    @Override
    public int hashCode() {
        Long id = this.getIdCentro().getIdCentro() + this.idEstudio + this.getIdCentro().getIdUniversidad();
        return id.hashCode();
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

/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: EstudioDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import com.google.gson.Gson;
import em.bd.compositekeys.CompositeKeyEstudio;
import java.io.Serializable;

public class EstudioDTO implements Serializable {

    private CompositeKeyEstudio id = new CompositeKeyEstudio();
    private CentroDTO centro = new CentroDTO();
    private AreaEstudiosDTO areaEstudios = new AreaEstudiosDTO();
    private String nombreEstudio = "";
    private CoordinadorDTO coordinador = new CoordinadorDTO();

    /**
     * Construye un estudio con parámetros.
     *
     * @param _idEstudio
     * @param _centro
     * @param _nombreEstudio
     * @param _areaEstudios
     * @param _coordinador
     */
    public EstudioDTO(Long _idEstudio, CentroDTO _centro, String _nombreEstudio, AreaEstudiosDTO _areaEstudios, CoordinadorDTO _coordinador) {
        if (_idEstudio == null) {
            this.id = new CompositeKeyEstudio(1L, _centro.getId());
        } else {
            this.id = new CompositeKeyEstudio(_idEstudio, _centro.getId());
        }
        this.centro = _centro;
        this.areaEstudios = _areaEstudios;
        this.nombreEstudio = _nombreEstudio;
        this.coordinador = _coordinador;
    }

    /**
     * Construye un estudio sin parámetros.
     *
     */
    public EstudioDTO() {

    }

    /**
     * Devuelve el identificador del estudio.
     *
     * @return
     */
    public CompositeKeyEstudio getId() {
        return id;
    }

    /**
     * Establece el id del estudio.
     *
     * @param _id
     */
    public void setId(CompositeKeyEstudio _id) {
        this.id = _id;
    }

    /**
     * Devuelve el centro donde se imparte el estudio.
     *
     * @return
     */
    public CentroDTO getCentro() {
        return centro;
    }

    /**
     * Establece el centro donde se imparte el estudio.
     *
     * @param _centro
     */
    public void setCentro(CentroDTO _centro) {
        this.centro = _centro;
    }

    /**
     * Devuelve el area de estudios al que pertenece el estudio.
     *
     * @return
     */
    public AreaEstudiosDTO getAreaEstudios() {
        return areaEstudios;
    }

    /**
     * Establece el area de estudios al que pertenece el estudio.
     *
     * @param _areaEstudios
     */
    public void setAreaEstudios(AreaEstudiosDTO _areaEstudios) {
        this.areaEstudios = _areaEstudios;
    }

    /**
     * Devuelve el nombre del estudio.
     *
     * @return
     */
    public String getNombreEstudio() {
        return nombreEstudio;
    }

    /**
     * Establece el nombre del estudio.
     *
     * @param _nombreEstudio
     */
    public void setNombreEstudio(String _nombreEstudio) {
        this.nombreEstudio = _nombreEstudio;
    }

    /**
     * Devuelve el coordinador del estudio.
     *
     * @return
     */
    public CoordinadorDTO getCoordinador() {
        return coordinador;
    }

    /**
     * Establece el coordinador del estudio.
     *
     * @param _coordinador
     */
    public void setCoordinador(CoordinadorDTO _coordinador) {
        this.coordinador = _coordinador;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "EstudioDTO{" + "idEstudio=" + id.getIdEstudio() + ", idCentro="
                + id.getIdCentro().getIdCentro() + ", idUniversidad=" + id.getIdCentro().getIdUniversidad()
                + ", nombreEstudio=" + nombreEstudio + ", centro" + centro.toString() + '}';
    }

    /**
     * Sobreescribe método hashCode().
     *
     * @return
     */
    @Override
    public int hashCode() {
        Long id = Long.valueOf(this.id.getIdEstudio() + "" + this.id.getIdCentro().getIdCentro()
                + "" + this.id.getIdCentro().getIdUniversidad());

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
        Long id = Long.valueOf(this.id.getIdEstudio() + "" + this.id.getIdCentro().getIdCentro()
                + "" + this.id.getIdCentro().getIdUniversidad());

        if (!(_object instanceof EstudioDTO)) {
            return false;
        }
        EstudioDTO other = (EstudioDTO) _object;
        Long idOther = Long.valueOf(other.id.getIdEstudio() + "" + other.id.getIdCentro().getIdCentro()
                + "" + other.id.getIdCentro().getIdUniversidad());
        return !((id == null && idOther != null) || (id != null && !id.equals(idOther)));
    }

    /**
     * Sobreescribe método toJson().
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

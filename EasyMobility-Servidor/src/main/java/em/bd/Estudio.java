/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Estudio.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd;

import com.google.gson.Gson;
import em.bd.compositekeys.CompositeKeyEstudio;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ESTUDIO")
public class Estudio implements Serializable {

    @EmbeddedId
    private CompositeKeyEstudio id;

    @Column(name = "NOMBRE")
    private String nombreEstudio;

    //Clave ajena de Centro
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "FK_CENTRO", referencedColumnName = "CE_PK_ID"),
        @JoinColumn(name = "FK_UNIVERSIDAD", referencedColumnName = "FK_UNIVERSIDAD")
    })
    @MapsId("idCentro")
    private Centro centro;

    //Clave ajena de AreaEstudios
    @ManyToOne
    @JoinColumn(name = "FK_AREAESTUDIOS", referencedColumnName = "PK_ID")
    private AreaEstudios areaEstudios;
    
    private Coordinador coordinador;
    
    //Clave ajena de AcuerdoEstudio (atributo ignorado para la serialización/deserialización)
    @JsonbTransient
    @OneToMany(mappedBy = "estudioOrigen")
    private List<AcuerdoEstudios> acuerdosOrigen;

    // Clave ajena de AcuerdoEstudio en la relación uno a muchos con la entidad Acuerdo de salida (atributo ignorado para la serialización/deserialización)
    @JsonbTransient
    @OneToMany(mappedBy = "estudioDestino")
    private List<AcuerdoEstudios> acuerdosDestino;

    /**
     * Construye un estudio sin parámetros.
     * 
     */
    public Estudio() {
    }

    /**
     * Construye un estudio con parámetros.
     * 
     * @param _idEstudio
     * @param _nombreEstudio
     * @param _centro
     * @param _areaEstudio
     * @param _coordinador 
     */
    public Estudio(Long _idEstudio, String _nombreEstudio, Centro _centro, 
            AreaEstudios _areaEstudio, Coordinador _coordinador) {
        this.centro = _centro;
        this.nombreEstudio = _nombreEstudio;
        this.areaEstudios = _areaEstudio;
        this.coordinador = _coordinador;
        this.id = new CompositeKeyEstudio(_idEstudio, _centro.getId());
    }

    /**
     * Devuelve el id del estudio.
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
     * Devuelve el centro del estudio.
     * 
     * @return 
     */
    public Centro getCentro() {
        return centro;
    }

    /**
     * Establece el centro del estudio.
     * 
     * @param _centro 
     */
    public void setCentro(Centro _centro) {
        this.centro = _centro;
    }

    /**
     * Devuelve el area del estudio.
     * 
     * @return 
     */
    public AreaEstudios getAreaEstudios() {
        return areaEstudios;
    }

    /**
     * Establece el area del estudio.
     * 
     * @param _areaEstudios 
     */
    public void setAreaEstudios(AreaEstudios _areaEstudios) {
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
    public Coordinador getCoordinador() {
        return coordinador;
    }

    /**
     * Establece el coordinador del estudio.
     * 
     * @param _coordinador 
     */
    public void setCoordinador(Coordinador _coordinador) {
        this.coordinador = _coordinador;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "Estudio{" + "idEstudio=" + id.getIdEstudio() + ", idCentro="
                + id.getIdCentro().getIdCentro() + ", idUniversidad=" + id.getIdCentro().getIdUniversidad()
                + ", nombre=" + nombreEstudio + ", centro" + centro.toString() + '}';
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
        Long id = Long.parseLong(this.id.getIdEstudio() + "" + this.id.getIdCentro().getIdCentro()
                + "" + this.id.getIdCentro().getIdUniversidad());

        if (!(_object instanceof Estudio)) {
            return false;
        }
        Estudio other = (Estudio) _object;
        Long idOther = Long.parseLong(other.id.getIdEstudio() + "" + other.id.getIdCentro().getIdCentro()
                + "" + other.id.getIdCentro().getIdUniversidad());
        return !((id == null && idOther != null) || (id != null && !id.equals(idOther)));
    }

    /**
     * Devuelve el estudio en formato JSON.
     * 
     * @return 
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

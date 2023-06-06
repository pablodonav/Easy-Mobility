/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Centro.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd;

import com.google.gson.Gson;
import em.bd.compositekeys.CompositeKeyCentro;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CENTRO")
public class Centro implements Serializable {

    @EmbeddedId
    private CompositeKeyCentro id;

    @Column(name = "NOMBRE")
    private String nombre;

    //Clave ajena de Universidad 
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_UNIVERSIDAD", referencedColumnName = "UN_PK_ID")
    @MapsId("idUniversidad")
    private Universidad universidad;

    //Clave ajena de Localización
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_LOCALIZACION", referencedColumnName = "PK_ID")
    private Localizacion localizacion;

    //Clave ajena de Estudio (atributo ignorado para la serialización/deserialización)
    @JsonbTransient
    @OneToMany(mappedBy = "centro", cascade = CascadeType.ALL)
    private List<Estudio> estudios;

    /**
     * Construye un centro con parámetros.
     *
     * @param idCentro
     * @param nombre
     * @param universidad
     * @param localizacion
     */
    public Centro(Long idCentro, String nombre, Universidad universidad, Localizacion localizacion) {
        this.id = new CompositeKeyCentro(idCentro, universidad.getId());
        this.nombre = nombre;
        this.universidad = universidad;
        this.localizacion = localizacion;
    }

    /**
     * Construye un centro sin parámetros.
     *
     */
    public Centro() {
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
     * Devuelve la universidad del centro.
     *
     * @return
     */
    public Universidad getUniversidad() {
        return universidad;
    }

    /**
     * Establece la universidad del centro.
     *
     * @param _universidad
     */
    public void setUniversidad(Universidad _universidad) {
        this.universidad = _universidad;
    }

    /**
     * Devuelve la localizacion del centro.
     *
     * @return
     */
    public Localizacion getLocalizacion() {
        return localizacion;
    }

    /**
     * Establece la localizacion del centro.
     *
     * @param _localizacion
     */
    public void setLocalizacion(Localizacion _localizacion) {
        this.localizacion = _localizacion;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "Centro{" + "id=" + id.getIdCentro() + ", nombre=" + nombre
                + ", idUniversidad=" + id.getIdUniversidad()
                + ", localizacion=" + localizacion + '}';
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
        Long id = Long.parseLong(this.id.getIdCentro() + "" + this.id.getIdUniversidad());

        if (!(_object instanceof Centro)) {
            return false;
        }
        Centro other = (Centro) _object;
        Long idOther = Long.parseLong(other.id.getIdCentro() + "" + other.id.getIdUniversidad());
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

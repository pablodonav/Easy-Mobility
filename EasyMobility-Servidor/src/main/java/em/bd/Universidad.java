/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Universidad.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UNIVERSIDAD")
public class Universidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UN_PK_ID")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    //Clave ajena de Localización
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_LOCALIZACION", referencedColumnName = "PK_ID")
    private Localizacion localizacion;

    // Clave ajena Centro (atributo ignorado para serialización/deserialización)
    @JsonbTransient
    @OneToMany(mappedBy = "universidad", cascade = CascadeType.ALL)
    private List<Centro> centrosAdscritos;

    /**
     * Construya una universidad con parámetros.
     * 
     * @param _nombre
     * @param _localizacion 
     */
    public Universidad(String _nombre, Localizacion _localizacion) {
        this.nombre = _nombre;
        this.localizacion = _localizacion;
    }

    /**
     * Construye una universidad sin parámetros.
     * 
     */
    public Universidad() {
    }

    /**
     * Devuelve el id de la universidad.
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id de la universidad.
     * 
     * @param _id 
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve el nombre de la universidad.
     * 
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la universidad.
     * 
     * @param _nombre 
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    /**
     * Devuelve la localizacion de la universidad.
     * 
     * @return 
     */
    public Localizacion getLocalizacion() {
        return localizacion;
    }

    /**
     * Establece la localizacion de la universidad.
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
        return "Universidad{" + "id=" + id + ", nombre=" + nombre + '}';
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
        if (!(_object instanceof Universidad)) {
            return false;
        }
        Universidad other = (Universidad) _object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * Devuelve la universidad en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

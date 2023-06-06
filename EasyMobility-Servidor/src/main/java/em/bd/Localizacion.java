/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Localizacion.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd;

import com.google.gson.Gson;
import java.io.Serializable;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LOCALIZACION")
public class Localizacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private Long id;

    @Column(name = "CIUDAD")
    private String ciudad;
    @Column(name = "PAIS")
    private String pais;
    @Column(name = "DIRECCION")
    private String direccion;

    // Resto de atributos y métodos
    // Relación con Universidad (atributo ignorado para la serialización/deserialización)
    @JsonbTransient
    @OneToOne(mappedBy = "localizacion")
    private Universidad universidad;

    // Relación con Centro (atributo ignorado para la serialización/deserialización)
    @JsonbTransient
    @OneToOne(mappedBy = "localizacion")
    private Centro centro;

    /**
     * Construye una localizacion con parámetros.
     * 
     * @param _direccion
     * @param _ciudad
     * @param _pais 
     */
    public Localizacion(String _direccion, String _ciudad, String _pais) {
        this.direccion = _direccion;
        this.ciudad = _ciudad;
        this.pais = _pais;
    }

    /**
     * Construye una localización sin parámetros.
     * 
     */
    public Localizacion() {
    }

    /**
     * Devuelve el id de la localizacion.
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id de la localizacion.
     * 
     * @param _id 
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve la direccion de la localizacion.
     * 
     * @return 
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la direccion de la localizacion.
     * 
     * @param _direccion 
     */
    public void setDireccion(String _direccion) {
        this.direccion = _direccion;
    }

    /**
     * Devuelve la ciudad de la localizacion.
     * 
     * @return 
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad de la localizacion.
     * 
     * @param _ciudad 
     */
    public void setCiudad(String _ciudad) {
        this.ciudad = _ciudad;
    }

    /**
     * Devuelve el pais de la localizacion.
     * 
     * @return 
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el pais de la localizacion.
     * 
     * @param _pais 
     */
    public void setPais(String _pais) {
        this.pais = _pais;
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
        if (!(_object instanceof Localizacion)) {
            return false;
        }
        Localizacion other = (Localizacion) _object;
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
     * Devuelve la localizacion en formato JSON.
     * 
     * @return 
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AreaEstudios.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AREAESTUDIOS")
public class AreaEstudios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    //Clave ajena de Estudio (atributo ignorado para serialización/deserialización)
    @JsonbTransient
    @OneToMany(mappedBy = "areaEstudios")
    private List<Estudio> estudios;

    /**
     * Construye un area de estudios con parámetros.
     * 
     * @param _nombre 
     */
    public AreaEstudios(String _nombre) {
        this.nombre = _nombre;
    }

    /**
     * Construye un area de estudios sin parámetros.
     * 
     */
    public AreaEstudios() {
    }

    /**
     * Devuelve el identificador del area de estudios.
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del area de estudios.
     * 
     * @param _id 
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve el nombre del area de estudios.
     * 
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del area de estudios.
     * 
     * @param _nombre 
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "AreaEstudios{" + "id=" + id + ", nombre=" + nombre + '}';
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
        if (!(_object instanceof AreaEstudios)) {
            return false;
        }
        AreaEstudios other = (AreaEstudios) _object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Devuelve el area de estudios en formato JSON.
     * 
     * @return 
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

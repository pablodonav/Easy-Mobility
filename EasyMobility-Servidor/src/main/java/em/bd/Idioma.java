/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Idioma.java
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
@Table(name = "IDIOMA")
public class Idioma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    //Clave ajena de AcuerdoEstudios (atributo ignorado para la serialización/deserialización)
    @JsonbTransient
    @OneToMany(mappedBy = "idioma")
    private List<AcuerdoEstudios> acuerdos;

    /**
     * Construye un idioma con parámetros.
     * 
     * @param _id
     * @param _nombre 
     */
    public Idioma(Long _id, String _nombre) {
        this.id = _id;
        this.nombre = _nombre;
    }

    /**
     * Construye un idioma sin parámetros.
     * 
     */
    public Idioma() {
    }

    /**
     * Devuelve el identificador del idioma.
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id del idioma.
     * 
     * @param _id 
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve el nombre del idioma.
     * 
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del idioma.
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
        return "IdiomaDTO{" + "id=" + id + ", nombre=" + nombre + '}';
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
        if (!(_object instanceof Idioma)) {
            return false;
        }
        Idioma other = (Idioma) _object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * Devuelve el idioma en formato JSON.
     * 
     * @return 
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

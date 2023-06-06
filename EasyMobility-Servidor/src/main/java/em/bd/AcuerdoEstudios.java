/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AcuerdoEstudios.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd;

import com.google.gson.Gson;
import em.common.enums.PeriodoIntercambio;
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ACUERDO")
public class AcuerdoEstudios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private Long id;

    @Column(name = "CURSO_ACADEMICO")
    private String cursoAcademico;      // Ej: 2022-23, 2023-24.
    @Column(name = "PERIODO_INTERCAMBIO")
    private PeriodoIntercambio periodoIntercambio;      // Completo o parcial.

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "FK_ESTUDIO_IN", referencedColumnName = "ES_PK_ID"),
        @JoinColumn(name = "FK_CENTRO_IN", referencedColumnName = "FK_CENTRO"),
        @JoinColumn(name = "FK_UNIVERSIDAD_IN", referencedColumnName = "FK_UNIVERSIDAD")
    })
    private Estudio estudioOrigen;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "FK_ESTUDIO_OUT", referencedColumnName = "ES_PK_ID"),
        @JoinColumn(name = "FK_CENTRO_OUT", referencedColumnName = "FK_CENTRO"),
        @JoinColumn(name = "FK_UNIVERSIDAD_OUT", referencedColumnName = "FK_UNIVERSIDAD")
    })
    private Estudio estudioDestino;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ALUMNO", referencedColumnName = "NUM_IDENTIFICACION")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "FK_IDIOMA", referencedColumnName = "PK_ID")
    private Idioma idioma;
    
    //Clave ajena de Fichero (atributo ignorado para la serialización/deserialización)
    @JsonbTransient
    @OneToMany(mappedBy = "acuerdo")
    private List<Fichero> ficheros;

    /**
     * Construye un acuerdo de estudios sin parámetros.
     * 
     */
    public AcuerdoEstudios() {
    }

    /**
     * Construye un acuerdo de estudios con parámetros.
     * 
     * @param _estudioOrigen
     * @param _estudioDestino
     * @param _cursoAcademico
     * @param _periodoIntercambio
     * @param _alumno
     * @param _idioma 
     */
    public AcuerdoEstudios(Estudio _estudioOrigen, Estudio _estudioDestino, String _cursoAcademico,
            PeriodoIntercambio _periodoIntercambio, Alumno _alumno, Idioma _idioma) {
        this.estudioOrigen = _estudioOrigen;
        this.estudioDestino = _estudioDestino;
        this.cursoAcademico = _cursoAcademico;
        this.periodoIntercambio = _periodoIntercambio;
        this.alumno = _alumno;
        this.idioma = _idioma;
    }

    /**
     * Devuelve el id de un acuerdo.
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id de un acuerdo.
     * 
     * @param _id 
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve el estudio de origen de un acuerdo.
     * 
     * @return 
     */
    public Estudio getEstudioOrigen() {
        return estudioOrigen;
    }

    /**
     * Establece el estudio de origen de un acuerdo.
     * 
     * @param _estudioOrigen 
     */
    public void setEstudioOrigen(Estudio _estudioOrigen) {
        this.estudioOrigen = _estudioOrigen;
    }

    /**
     * Devuelve el estudio de destino de un acuerdo.
     * 
     * @return 
     */
    public Estudio getEstudioDestino() {
        return estudioDestino;
    }

    /**
     * Establece el estudio de destino de un acuerdo.
     * 
     * @param _estudioDestino 
     */
    public void setEstudioDestino(Estudio _estudioDestino) {
        this.estudioDestino = _estudioDestino;
    }

    /**
     * Devuelve el alumno de un acuerdo.
     * 
     * @return 
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno de un acuerdo.
     * 
     * @param _alumno 
     */
    public void setAlumno(Alumno _alumno) {
        this.alumno = _alumno;
    }

    /**
     * Devuelve el idioma del acuerdo.
     * 
     * @return 
     */
    public Idioma getIdioma() {
        return idioma;
    }

    /**
     * Establece el idioma del acuerdo.
     * 
     * @param _idioma 
     */
    public void setIdioma(Idioma _idioma) {
        this.idioma = _idioma;
    }

    /**
     * Devuelve el curso academico del acuerdo.
     * 
     * @return 
     */
    public String getCursoAcademico() {
        return cursoAcademico;
    }

    /**
     * Establece el curso academico del acuerdo.
     * 
     * @param _cursoAcademico 
     */
    public void setCursoAcademico(String _cursoAcademico) {
        this.cursoAcademico = _cursoAcademico;
    }

    /**
     * Devuelve el periodo de intercambio.
     * 
     * @return 
     */
    public PeriodoIntercambio getPeriodoIntercambio() {
        return periodoIntercambio;
    }

    /**
     * Establece el periodo de intercambio del acuerdo.
     * 
     * @param _periodoIntercambio 
     */
    public void setPeriodoIntercambio(PeriodoIntercambio _periodoIntercambio) {
        this.periodoIntercambio = _periodoIntercambio;
    }

    /**
     * Sobreescribe método toString()
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "AcuerdoEstudios{" + "id=" + id + ", cursoAcademico=" + cursoAcademico
                + ", periodoIntercambio=" + periodoIntercambio + '}';
    }

    /**
     * Sobreescribe método hashCode().
     *
     * @return
     */
    @Override
    public int hashCode() {
        Long ident = Long.valueOf(estudioOrigen.getId().getIdCentro().getIdCentro() + ""
                + estudioOrigen.getId().getIdCentro().getIdUniversidad() + ""
                + estudioDestino.getId().getIdCentro().getIdCentro() + ""
                + estudioDestino.getId().getIdCentro().getIdUniversidad());

        int hash = 0;
        hash += (ident != null ? ident.hashCode() : 0);
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
        Long ident = Long.valueOf(this.estudioOrigen.getId().getIdCentro().getIdCentro() + ""
                + this.estudioOrigen.getId().getIdCentro().getIdUniversidad() + ""
                + this.estudioDestino.getId().getIdCentro().getIdCentro() + ""
                + this.estudioDestino.getId().getIdCentro().getIdUniversidad());

        if (!(_object instanceof AcuerdoEstudios)) {
            return false;
        }
        AcuerdoEstudios other = (AcuerdoEstudios) _object;
        Long idOther = Long.valueOf(other.estudioOrigen.getId().getIdCentro().getIdCentro() + ""
                + other.estudioOrigen.getId().getIdCentro().getIdUniversidad() + ""
                + other.estudioDestino.getId().getIdCentro().getIdCentro() + ""
                + other.estudioDestino.getId().getIdCentro().getIdUniversidad());
        return !((ident == null && idOther != null)
                || (ident != null && !ident.equals(idOther)));
    }

    /**
     * Devuelve el acuerdo de estudios en formato JSON.
     *
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

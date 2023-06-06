/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AcuerdoEstudiosDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import com.google.gson.Gson;
import em.common.enums.PeriodoIntercambio;
import java.io.Serializable;

public class AcuerdoEstudiosDTO implements Serializable {

    private Long id = 0L;
    private EstudioDTO estudioOrigen = new EstudioDTO();         // Centro de entrada del alumno.
    private EstudioDTO estudioDestino = new EstudioDTO();        // Centro de salida del alumno.
    private AlumnoDTO alumno = new AlumnoDTO();
    private IdiomaDTO idioma = new IdiomaDTO();
    private String cursoAcademico = "";      // Ej: 2022, 2023.
    private PeriodoIntercambio periodoIntercambio = PeriodoIntercambio.COMPLETO;      // Completo o parcial.

    /**
     * Construye un acuerdo de estudios con parámetros.
     *
     * @param _idAcuerdo
     * @param _estudioOrigen
     * @param _estudioDestino
     * @param _idioma
     * @param _alumno
     * @param _cursoAcademico
     * @param _periodoIntercambio
     *
     */
    public AcuerdoEstudiosDTO(Long _idAcuerdo, EstudioDTO _estudioOrigen, EstudioDTO _estudioDestino, String _cursoAcademico,
            PeriodoIntercambio _periodoIntercambio, AlumnoDTO _alumno, IdiomaDTO _idioma) {
        this.id = _idAcuerdo;
        this.estudioOrigen = _estudioOrigen;
        this.estudioDestino = _estudioDestino;
        this.cursoAcademico = _cursoAcademico;
        this.periodoIntercambio = _periodoIntercambio;
        this.alumno = _alumno;
        this.idioma = _idioma;
    }

    /**
     * Construye un acuerdo de estudios sin parámetros.
     *
     */
    public AcuerdoEstudiosDTO() {
    }

    /**
     * Devuelve el identificador del acuerdo de estudios.
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del acuerdo de estudios.
     *
     * @param _id
     */
    public void setId(Long _id) {
        this.id = _id;
    }

    /**
     * Devuelve el estudio de origen del acuerdo.
     *
     * @return
     */
    public EstudioDTO getEstudioOrigen() {
        return estudioOrigen;
    }

    /**
     * Establece el estudio de origen del acuerdo.
     *
     * @param _estudioOrigen
     */
    public void setEstudioOrigen(EstudioDTO _estudioOrigen) {
        this.estudioOrigen = _estudioOrigen;
    }

    /**
     * Devuelve el estudio de destino del acuerdo.
     *
     * @return
     */
    public EstudioDTO getEstudioDestino() {
        return estudioDestino;
    }

    /**
     * Establece el estudio de destino del acuerdo.
     *
     * @param _estudioDestino
     */
    public void setEstudioDestino(EstudioDTO _estudioDestino) {
        this.estudioDestino = _estudioDestino;
    }

    /**
     * Devuelve el idioma que va a impartir el alumno en el acuerdo.
     *
     * @return
     */
    public IdiomaDTO getIdioma() {
        return idioma;
    }

    /**
     * Establece el idioma que va a impartir el alumno en el acuerdo.
     *
     * @param _idioma
     */
    public void setIdioma(IdiomaDTO _idioma) {
        this.idioma = _idioma;
    }

    /**
     * Devuelve el alumno del acuerdo de estudios.
     *
     * @return
     */
    public AlumnoDTO getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno del acuerdo de estudios.
     *
     * @param _alumno
     */
    public void setAlumno(AlumnoDTO _alumno) {
        this.alumno = _alumno;
    }

    /**
     * Devuelve el curso académico del acuerdo de estudios.
     *
     * @return
     */
    public String getCursoAcademico() {
        return cursoAcademico;
    }

    /**
     * Establece el curso académico del acuerdo de estudios.
     *
     * @param _cursoAcademico
     */
    public void setCursoAcademico(String _cursoAcademico) {
        this.cursoAcademico = _cursoAcademico;
    }

    /**
     * Devuelve el periodo de intercambio del acuerdo de estudios.
     *
     * @return
     */
    public PeriodoIntercambio getPeriodoIntercambio() {
        return periodoIntercambio;
    }

    /**
     * Establece el periodo de intercambio del acuerdo de estudios.
     *
     * @param _periodoIntercambio
     */
    public void setPeriodoIntercambio(PeriodoIntercambio _periodoIntercambio) {
        this.periodoIntercambio = _periodoIntercambio;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return "AcuerdoEstudiosDTO{" + "id=" + id + '}';
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
                + estudioDestino.getId().getIdCentro().getIdCentro());

        int hash = 0;
        hash += (ident != null ? ident.hashCode() : 0);
        return hash;
    }

    /**
     * Sobreescribe método equals().
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object _object) {
        Long ident = Long.valueOf(this.estudioOrigen.getId().getIdCentro().getIdCentro() + ""
                + this.estudioOrigen.getId().getIdCentro().getIdUniversidad() + ""
                + this.estudioDestino.getId().getIdCentro().getIdCentro() + ""
                + this.estudioDestino.getId().getIdCentro().getIdUniversidad());

        if (!(_object instanceof AcuerdoEstudiosDTO)) {
            return false;
        }
        AcuerdoEstudiosDTO other = (AcuerdoEstudiosDTO) _object;
        Long idOther = Long.valueOf(other.estudioOrigen.getId().getIdCentro().getIdCentro() + ""
                + other.estudioOrigen.getId().getIdCentro().getIdUniversidad() + ""
                + other.estudioDestino.getId().getIdCentro().getIdCentro() + ""
                + other.estudioDestino.getId().getIdCentro().getIdUniversidad());
        if ((ident == null && idOther != null)
                || (ident != null && !ident.equals(idOther))) {
            return false;
        }
        return true;
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

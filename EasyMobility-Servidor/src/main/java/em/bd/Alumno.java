/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Alumno.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.bd;

import em.common.enums.TipoIdentificacion;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ALUMNO")
public class Alumno extends Usuario implements Serializable {

    // Clave ajena de AcuerdoEstudio (atributo ignorado para serialización/deserialización)
    @JsonbTransient
    @OneToOne(mappedBy = "alumno")
    private AcuerdoEstudios acuerdo;

    /**
     * Construye un alumno con parámetros.
     *
     * @param numIdentificacion
     * @param documentoIdentificacion
     * @param telefono
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param correo
     * @param hashContrasenya
     * @param haCambiadoPwd
     */
    public Alumno(String numIdentificacion, TipoIdentificacion documentoIdentificacion,
            Integer telefono, String nombre, String apellido1, String apellido2, String correo,
            String hashContrasenya, boolean haCambiadoPwd) {
        super(numIdentificacion, documentoIdentificacion, telefono, nombre, apellido1, apellido2, correo, hashContrasenya, haCambiadoPwd);
    }

    /**
     * Construye un alumno sin parámetros.
     *
     */
    public Alumno() {
    }
}

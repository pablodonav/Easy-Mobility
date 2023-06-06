/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Fichero.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate
 */
package em.bd;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FICHERO")
public class Fichero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    // id del fichero
    
    private String fileName;  // nombre original, nos permite conservar el nombre al recuperarlo de la BD
    private String mimeType;  // qué tipo de fichero es, nos permite lanzar el visor adecuado (si es un tipo habitual: jpeg. mp3...)
    
    @Lob
    private byte[] file;    // el condumio
    
    @ManyToOne
    @JoinColumn(name = "FK_ACUERDO", referencedColumnName = "PK_ID")
    private AcuerdoEstudios acuerdo;

    /**
     * Construye un fichero con parámetros.
     * 
     * @param _fileName
     * @param _mimeType
     * @param _file
     * @param _acuerdo 
     */
    public Fichero(String _fileName, String _mimeType, byte[] _file, AcuerdoEstudios _acuerdo) {
        this.fileName = _fileName;
        this.mimeType = _mimeType;
        this.file = _file;
        this.acuerdo = _acuerdo;
    }

    /**
     * Construye un fichero sin parámetros.
     * 
     */
    public Fichero() {
    }

    /**
     * Devuelve el acuerdo del fichero.
     * 
     * @return 
     */
    public AcuerdoEstudios getAcuerdo() {
        return acuerdo;
    }

    /**
     * Establece el acuerdo del fichero.
     * 
     * @param _acuerdo 
     */
    public void setAcuerdo(AcuerdoEstudios _acuerdo) {
        this.acuerdo = _acuerdo;
    }

    /**
     * Devuelve el id del fichero.
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id del fichero.
     * 
     * @param _id 
     */
    public void setId(Long _id) {
        this.id = _id;
    }
    
    /**
     * Devuelve el nombre del fichero.
     * 
     * @return 
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Establece el nombre del fichero.
     * 
     * @param _fileName 
     */
    public void setFileName(String _fileName) {
        this.fileName = _fileName;
    }

    /**
     * Devuelve el tipo mime del fichero.
     * 
     * @return 
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Establece el tipo mime del fichero.
     * 
     * @param _mimeType 
     */
    public void setMimeType(String _mimeType) {
        this.mimeType = _mimeType;
    }

    /**
     * Devuelve los bytes que forman el fichero.
     * 
     * @return 
     */
    public byte[] getFile() {
        return file;
    }

    /**
     * Establece los bytes que forman el fichero.
     * 
     * @param _file 
     */
    public void setFile(byte[] _file) {
        this.file = _file;
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
     * @param object
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fichero)) {
            return false;
        }
        Fichero other = (Fichero) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * Sobreescribe método toString().
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "demo.db.Fichero[ id=" + id + " ]";
    }
}

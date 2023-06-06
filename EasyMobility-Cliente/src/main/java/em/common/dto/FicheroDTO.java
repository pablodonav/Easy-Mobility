/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: FicheroDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import java.io.Serializable;

public class FicheroDTO implements Serializable {

    private Long id = 0L;    // id del fichero

    private String fileName = "";  // nombre original, nos permite conservar el nombre al recuperarlo de la BD
    private String mimeType = "";  // qué tipo de fichero es, nos permite lanzar el visor adecuado (si es un tipo habitual: jpeg. mp3...)

    private byte[] file = new byte[0];    // el condumio

    private AcuerdoEstudiosDTO acuerdo = new AcuerdoEstudiosDTO();

    /**
     * Construye un fichero con parámetros.
     *
     * @param _id
     * @param _fileName
     * @param _mimeType
     * @param _file
     * @param _acuerdo
     */
    public FicheroDTO(Long _id, String _fileName, String _mimeType, byte[] _file, AcuerdoEstudiosDTO _acuerdo) {
        this.id = _id;
        this.fileName = _fileName;
        this.mimeType = _mimeType;
        this.file = _file;
        this.acuerdo = _acuerdo;
    }

    /**
     * Construye un fichero sin parámetros.
     *
     */
    public FicheroDTO() {
    }

    /**
     * Devuelve el acuerdo asociado al fichero.
     *
     * @return
     */
    public AcuerdoEstudiosDTO getAcuerdo() {
        return acuerdo;
    }

    /**
     * Establece el acuerdo asociado al fichero.
     *
     * @param _acuerdo
     */
    public void setAcuerdo(AcuerdoEstudiosDTO _acuerdo) {
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
     * Devuelve la cadena de bytes que forman el fichero.
     * 
     * @return 
     */
    public byte[] getFile() {
        return file;
    }

    /**
     * Establece la cadena de bytes que forman el fichero.
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
     * @param _object
     * @return
     */
    @Override
    public boolean equals(Object _object) {
        if (!(_object instanceof FicheroDTO)) {
            return false;
        }
        FicheroDTO other = (FicheroDTO) _object;
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

/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: TipoIdentificacion.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.enums;

public enum TipoIdentificacion {
    DNI("dni"),
    PASAPORTE("passport");

    private final String documentoIdentificacion;

    /**
     * Construye el tipo de identificación.
     *
     * @param _documentoIdentificacion
     */
    TipoIdentificacion(String _documentoIdentificacion) {
        this.documentoIdentificacion = _documentoIdentificacion;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return documentoIdentificacion;
    }
}

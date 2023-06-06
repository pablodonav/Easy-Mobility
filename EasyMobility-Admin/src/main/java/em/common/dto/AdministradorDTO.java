/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: AdministradorDTO.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.dto;

import em.common.enums.TipoIdentificacion;
import java.io.Serializable;

public class AdministradorDTO extends UsuarioDTO implements Serializable {

    /**
     * Construye un administrador con parámetros.
     *
     * @param _numIdentificacion
     * @param _documentoIdentificacion
     * @param _telefono
     * @param _nombre
     * @param _apellido1
     * @param _apellido2
     * @param _correo
     * @param _hashContrasenya
     * @param _haCambiadoPwd
     */
    public AdministradorDTO(String _numIdentificacion, TipoIdentificacion _documentoIdentificacion, 
            Integer _telefono, String _nombre, String _apellido1,
            String _apellido2, String _correo, String _hashContrasenya, boolean _haCambiadoPwd) {
        super(_numIdentificacion, _documentoIdentificacion, 
                _telefono, _nombre, _apellido1, _apellido2, 
                _correo, _hashContrasenya, _haCambiadoPwd);
    }

    /**
     * Construye un administrador sin parámetros.
     *
     */
    public AdministradorDTO() {
        super();
    }
}

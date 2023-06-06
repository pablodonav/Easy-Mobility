/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: PeriodoIntercambio.java
    Date: 30 mar. 2023
  
    Authors: Pablo Doñate & Adnana Dragut
 */
package em.common.enums;

public enum PeriodoIntercambio {
    PARCIAL("parcial"),
    COMPLETO("completo");

    private final String duracionIntercambio;

    /**
     * Construye un periodo de intercambio.
     * 
     * @param _duracionIntercambio 
     */
    PeriodoIntercambio(String _duracionIntercambio) {
        this.duracionIntercambio = _duracionIntercambio;
    }

    /**
     * Sobreescribe método toString().
     *
     * @return
     */
    @Override
    public String toString() {
        return duracionIntercambio;
    }
}

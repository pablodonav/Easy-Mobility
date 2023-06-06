/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: EmailUtility.java
    Date: 23 abr. 2023
  
    Authors: Félix Serna
 */
package em;

import com.google.gson.Gson;

public class Mensaje {

    private String json;
    private String cmd;

    public Mensaje(String _json) {
        this.json = _json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getJson() {
        return this.json;
    }

    public String getCmd() {
        return this.cmd;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

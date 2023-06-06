/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: WebSocketManager.java
    Date: 30 mar. 2023
  
    Authors: Félix Serna
 */
package em.websocket;

import em.Mensaje;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Stateless
@ServerEndpoint("/websocket")
public class WebSocketManager {

    private Session session; // Almacena última sesión que se ha conectado al socket para poder obtener getOpenSessions()

    @OnOpen //se dispara cuando un cliente se conecta al socket
    public void onOpen(Session _session) {
        this.session = _session;
    }

    @OnMessage
    public void onMessage(Session _session, String _message) {

        System.out.println("== RX ==> " + _message);
        Mensaje m = new Mensaje(_message);

        switch (m.getCmd()) { //Mensaje recibido
            case "newUser":
                break;
        }// switch
    }

    @OnClose
    public void onClose(Session _session) {
        System.out.println("--- Session " + _session.getId() + " has ended");
    }

    @OnError
    public void onError(Session _session, Throwable t) {
        System.out.println("--- ERROR in session " + _session.getId());
    }

    public void sendToClients(String _msg) {
        try {
            System.out.println("Mensaje de broadcast => " + _msg);
            for (Session s : session.getOpenSessions()) {
                System.out.println("Mensaje de broadcast => " + _msg);
                if (_msg != null) {
                    s.getBasicRemote().sendText(_msg);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WebSocketManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("LOGGER WSMANAGER ===> " + ex.getMessage());
        }
    }
}

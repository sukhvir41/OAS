/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.wsServices;

import WebSocketSettings.GlobalWsConfig;
import com.google.gson.JsonObject;
import entities.UserType;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author development
 */
@ServerEndpoint(value = "/admin/ws/systemInfo", configurator = GlobalWsConfig.class)
public class SystemInfoWS {
    
    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        System.out.println("connection opened");
        try {
            HttpSession httpSession = (HttpSession) conf.getUserProperties().get(GlobalWsConfig.SESSION);
            if ((boolean) httpSession.getAttribute("accept") == true && httpSession.getAttribute("type").equals(UserType.Admin)) {
                SystemInfoService.addSession(session);
            } else {
                SystemInfoService.sendError(session);
            }
        } catch (Exception exception) {
            SystemInfoService.sendError(session);
        }
    }
    
    @OnClose
    public void close(Session session) {
        SystemInfoService.removeSession(session);
    }
    
    @OnError
    public void onError(Throwable error) {
        System.out.println("error in WS Admin web socket");
        error.printStackTrace();
        // have to cehcck what to do here
    }
    
    @OnMessage
    public void handleMessage(String message, Session session) {
        
    }
    
   
}

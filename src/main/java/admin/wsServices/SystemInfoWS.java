/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.wsServices;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import WebSocketSettings.GlobalWsConfig;
import entities.UserType;

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
            if ((boolean) httpSession.getAttribute("accept") && httpSession.getAttribute("type").equals(UserType.Admin)) {
                session.setMaxIdleTimeout(120000l);
                SystemInfoService.addSession(session);
            } else {
                SystemInfoService.sendError(session);
                session.close();
            }
        } catch (Exception exception) {
            SystemInfoService.sendError(session);
            try {
                session.close();
            } catch (IOException ex) {
                //do nothing
            }
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

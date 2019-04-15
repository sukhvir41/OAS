package student.attendanceWsService;


import WebSocketSettings.GlobalWsConfig;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/test-ws/mac-test", configurator = GlobalWsConfig.class)
public class AttendanceWs {


    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        HttpSession httpSession = (HttpSession) conf.getUserProperties().get(GlobalWsConfig.SESSION);
        String theIp = (String) httpSession.getAttribute("ip");
        AttendanceService.getMac(theIp, session);

    }

    @OnClose
    public void close(Session session) {

    }

    @OnMessage
    public void handleMessage(String message, Session session) {

    }

}

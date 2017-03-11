/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author sukhvir
 */
@ApplicationScoped
@ServerEndpoint("/teacher/websocket")
public class TeacherWebSocketServer {

    @Inject
    private LectureSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {

    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);

    }

    @OnError
    public void onError(Throwable error) {

    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try {
            Gson gson = new Gson();
            Message messageObject = gson.fromJson(message, Message.class);
            sessionHandler.sendMessage(messageObject);
        } catch (JsonSyntaxException e) {
            sessionHandler.sendMessage(message, session);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

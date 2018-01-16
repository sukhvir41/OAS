/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.Lecture;
import entities.Student;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import utility.Utils;

/**
 *
 * @author development
 */
@ServerEndpoint("/studentWebsocket")
public class StudentWebSocket {

    private Map<Session, StudentWsInfo> studentsSession = new HashMap<>();
    private Gson gson = new Gson();

    @OnOpen
    public void open(Session session) {
        studentsSession.put(session, null);
        session.getAsyncRemote()
                .sendText(getConnectoinMessage("connection successful").toString());
    }

    @OnClose
    public void close(Session session) {
        session.getAsyncRemote()
                .sendText(getConnectoinMessage("connection terminated").toString());
    }

    @OnError
    public void onError(Throwable error) {
        // have to see what todo here
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws Exception {

        CompletableFuture.supplyAsync(() -> gson.fromJson(message, StudentSentInfo.class))
                .exceptionally((ex) ->  throw new Exception("error"))
                    .thenApply((info) -> this.getStudentWsInfo(info));

        //figure compatible futures error handling
    }

    private JsonObject getConnectoinMessage(String message) {
        JsonObject connectionStatus = new JsonObject();
        connectionStatus.addProperty("type", "connection");
        connectionStatus.addProperty("message", message);

        return connectionStatus;
    }

    private StudentWsInfo getStudentWsInfo(StudentSentInfo info) {
        try {
            Student student = Utils.getFromDbDB(session -> (Student) session.get(Student.class, info.getStudentId()))
                    .orElseThrow(NullPointerException::new);

            Lecture lecture = Utils.getFromDbDB(session -> (Lecture) session.get(Lecture.class, info.getLectureId()))
                    .orElseThrow(NullPointerException::new);

            return new StudentWsInfo(student, lecture);
        } catch (Exception e) {
            return null;
        }

    }

    private void error(Session session, String message) {
        session.getAsyncRemote()
                .sendText(getConnectoinMessage(message).toString());

    }
}

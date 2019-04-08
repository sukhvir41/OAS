/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import WebSocketSettings.GlobalWsConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.Lecture;
import entities.Teacher;
import entities.UserType;
import lombok.Getter;
import lombok.Setter;
import utility.Utils;

/**
 *
 * @author development
 */
@ServerEndpoint(value = "/ljlkf", configurator = GlobalWsConfig.class)//set the url
public class TeacherWebSocket {

    private static final Gson gson = new Gson();
    private static final JsonObject genricErrorMessage = new JsonObject();
    private static final String MESSAGE = "message";
    private static final String CONNECTION = "connection";

    static {
        genricErrorMessage.addProperty(MESSAGE, "oops! something went horribaly wrong or did it? ;-/)");
    }

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        try {
            HttpSession httpSession = (HttpSession) conf.getUserProperties().get("session");
            Teacher teacher = (Teacher) httpSession.getAttribute(UserType.Teacher.toString());

            if ((boolean) httpSession.getAttribute("accept")) {
                TeacherWsSession teacherSession = new TeacherWsSession(teacher, session);

            }

        } catch (Exception exception) {
        }

    }

    @OnClose
    public void close(Session session) {
        TeacherWsSession.removeSession(session);

    }

    @OnError

    public void onError(Throwable error) {
    }

    /*
    connection message 
    {
        lectureId: 3h3gr,
        teacherdId : 23
    }
    
    
     */
    @OnMessage
    public void handleMessage(String message, Session session) {
        try {
            TeacherJsonMessage jsonMessage = gson.fromJson(message, TeacherJsonMessage.class);

            boolean isValidTeacher = Utils.getFromDB((dbSession) -> isValidTeacher(dbSession, jsonMessage))
                    .orElseThrow(Exception::new);

            if (isValidTeacher) {
                Lecture lecture = Utils.getFromDB((dbSession) -> (Lecture) dbSession.get(Lecture.class, jsonMessage.lectureId))
                        .orElseThrow(Exception::new);

                Teacher teacher = Utils.getFromDB((dbSession) -> (Teacher) dbSession.get(Teacher.class, jsonMessage.teacherId))
                        .orElseThrow(Exception::new);

                TeacherWsSession wsSession = new TeacherWsSession(teacher, session);
                TeacherWsSession.addSession(session, wsSession);

                JsonObject json = new JsonObject();
                json.addProperty(CONNECTION, true);

                session.getAsyncRemote()
                        .sendText(TeacherMessage.CONNECTION.jsonToString(json));

            } else {
                sendGenericErrorMessage(session);
            }
        } catch (Exception execption) {
            sendGenericErrorMessage(session);
        }

    }

    private boolean isValidTeacher(org.hibernate.Session dbSession, TeacherJsonMessage jsonMessage) {
        Lecture lecture = (Lecture) dbSession.get(Lecture.class, jsonMessage.lectureId);
        Teacher teacher = (Teacher) dbSession.get(Teacher.class, jsonMessage.teacherId);

        return lecture.getTeaching().getTeacher().equals(teacher);

    }

    private void sendGenericErrorMessage(Session session) {
        session.getAsyncRemote()
                .sendText(TeacherMessage.ERROR.jsonToString(genricErrorMessage));
    }

    private class TeacherJsonMessage {

        @Getter
        @Setter
        private String lectureId;

        @Getter
        @Setter
        private String teacherId;

        public TeacherJsonMessage(String lectureId, String teacherId) {
            this.lectureId = lectureId;
            this.teacherId = teacherId;
        }

    }
}

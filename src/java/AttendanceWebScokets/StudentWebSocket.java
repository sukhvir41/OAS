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
import entities.UserType;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import utility.Utils;

/**
 *
 * @author development
 */
@ServerEndpoint(value = "", configurator = GlobalWsConfig.class) // have add the url
public class StudentWebSocket {

    private static final String MESSAGE = "message";
    private static final String CONNECTION = "connection";
    private static final Gson gson = new Gson();
    private static final JsonObject genricErrorMessage = new JsonObject();

    static {
        genricErrorMessage.addProperty(MESSAGE, "Wamp! Wamp! Wamp! something went horribaly wrong or did it? ;-/)");
    }

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        try {

            HttpSession httpSession = (HttpSession) conf.getUserProperties().get("session");
            Student student = (Student) httpSession.getAttribute(UserType.Student.toString());

            if ((boolean) httpSession.getAttribute("accept")) {

                StudentWsSession stduentSession = new StudentWsSession(student, session);
                StudentWsSession.addSession(session, stduentSession);

                JsonObject json = new JsonObject();
                json.addProperty(CONNECTION, true);

                session.getAsyncRemote()
                        .sendText(StudentMessage.CONNECTION.jsonToString(json));

            } else {
                throw new Exception();
            }

        } catch (Exception exception) {
            sendGenericErrorMessage(session);
        }
    }

    @OnClose
    public void close(Session session) {
        StudentWsSession.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        // have to cehcck what to do here
    }

    /*
        json format 
        
        {
        lectureId : Ab123h 
        }
     */
    @OnMessage
    public void handleMessage(String message, Session session) {
        try {

            StudentWsSession stduentSession = StudentWsSession.getSession(session)
                    .orElseThrow(Exception::new);

            StudentJsonMessage jsonMessage = gson.fromJson(message, StudentJsonMessage.class);

            Lecture lecture = Utils.getFromDB(
                    dbSession -> (Lecture) dbSession.get(Lecture.class, jsonMessage.lectureId))
                    .orElseThrow(Exception::new);

            stduentSession.setLecture(lecture);

            if (isValidStudent(stduentSession)) {
                
                
                
            } else {
                throw new Exception();
            }

        } catch (Exception exception) {
            sendGenericErrorMessage(session);
        }

    }

    private boolean isValidStudent(StudentWsSession studentSession) {
        return Utils.getFromDB(dbSession -> checkingStudentValid(dbSession, studentSession))
                .orElse(false);
    }

    private boolean checkingStudentValid(org.hibernate.Session dbSession, StudentWsSession studentSession) {
        Lecture lecture = (Lecture) dbSession.get(Lecture.class, studentSession.getLecture().getId());
        Student student = (Student) dbSession.get(Student.class, studentSession.getStudent().getId());

        return student.getClassRoom().getId() == lecture.getTeaching().getClassRoom().getId();
    }

    private void sendGenericErrorMessage(Session session) {
        session.getAsyncRemote()
                .sendText(StudentMessage.ERROR.jsonToString(genricErrorMessage));
    }

    private StudentWsSession getStudnet(Session sesssion, Map<Student, StudentWsSession> wsSessions) {
        return wsSessions.entrySet()
                .stream()
                .map(entry -> entry.getValue())
                .filter(studentSession -> studentSession.getWsSession().equals(sesssion))
                .findFirst()
                .orElse(null);
    }

    private class StudentJsonMessage {

        @Getter
        @Setter

        String lectureId;

        public StudentJsonMessage(String lectureId) {
            this.lectureId = lectureId;

        }

    }
}

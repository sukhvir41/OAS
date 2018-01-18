/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.Attendance;
import entities.Lecture;
import entities.Student;
import entities.Teaching;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author development
 */
@ServerEndpoint("/studentWebsocket")
public class StudentWebSocket {

    private final Gson gson = new Gson();

    @OnOpen
    public void open(Session session) {
        StudentSessions.addSession(session, null);
        WsMessages.sendConnectionSucessfull(session);
    }

    @OnClose
    public void close(Session session) {
        WsMessages.sendConnectionTerminated(session);
        StudentSessions.removeSession(session);

    }

    @OnError
    public void onError(Throwable error) {
        // have to see what todo here
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws Exception {

        CompletableFuture.supplyAsync(() -> gson.fromJson(message, StudentSentInfo.class))
                .exceptionally((error) -> throwError())
                .thenApply(sentInfo -> getStudentWsInfo(sentInfo))
                .exceptionally(error -> throwErrorOnWs(session, "Something is wrong"))
                .thenAccept(info -> markAttendance(info, session));
    }

    private void markAttendance(StudentWsInfo info, Session session) {
        boolean marked = Utils.getFromDB((dbSession) -> checkAttendance(dbSession, info), error -> WsMessages.sendErrorMessage(session, "Error in marking attendace"))
                .orElse(false);

        JsonObject json = new JsonObject();
        json.addProperty("lectureId", info.getLecutre().getId());
        json.addProperty("attendance marked", marked);
        if (marked) {
            json.addProperty("message", "attendance marked");
            //todo: send teacher notofication about student marked attendance
        } else {
            json.addProperty("message", "coudn't mark attendance");
        }

        WsMessages.sendAttendanceMessage(session, json);

    }

    private Boolean checkAttendance(org.hibernate.Session session, StudentWsInfo info) {
        Student student = (Student) session.get(Student.class, info.getStudent().getId());
        Lecture lecture = (Lecture) session.get(Lecture.class, info.getLecutre().getId());
        Teaching lectureTeaching = lecture.getTeaching();

        if (student.getClassRoom().equals(lectureTeaching.getClassRoom()) && student.getSubjects().contains(lectureTeaching.getSubject())) {
            if (Utils.checkTimeFactorStudentAttendance(lecture.getDate(), info.getTime())) {
                int count = (int) session.createCriteria(Attendance.class)
                        .add(Restrictions.eq("lecture", lecture))
                        .add(Restrictions.eq("student", student))
                        .setProjection(Projections.rowCount())
                        .uniqueResult();
                return count <= 0;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private <V> V throwError() {
        throw new RuntimeException("error");
    }

    private StudentWsInfo getStudentWsInfo(StudentSentInfo info) {
        try {
            Student student = Utils.getFromDB(session -> (Student) session.get(Student.class, info.getStudentId()))
                    .orElseThrow(NullPointerException::new);

            Lecture lecture = Utils.getFromDB(session -> (Lecture) session.get(Lecture.class, info.getLectureId()))
                    .orElseThrow(NullPointerException::new);

            return new StudentWsInfo(student, lecture, LocalDateTime.now());
        } catch (Exception e) {
            return null;
        }

    }

    private <V> V throwErrorOnWs(Session session, String message) {
        WsMessages.sendErrorMessage(session, message);

        throwError();
        return null;
    }
}

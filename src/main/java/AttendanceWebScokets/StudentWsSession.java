/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import entities.Lecture;
import entities.Student;
import java.net.InetAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.websocket.Session;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author development
 */
public class StudentWsSession {

    @Getter
    @Setter
    private Student student;
    @Getter
    @Setter
    private Lecture lecture;
    @Getter
    @Setter
    private InetAddress ipAddress;
    @Getter
    @Setter
    private Session wsSession;

    public StudentWsSession(Student student, Session wsSession) {
        this.student = student;
        this.wsSession = wsSession;
    }

    
    //==========================================================================
    
    private static final Map<Session, StudentWsSession> sessions = new ConcurrentHashMap<>();

    public static void addSession(Session wsSession, StudentWsSession theSession) {
        sessions.put(wsSession, theSession);
    }

    public static Optional<StudentWsSession> getSession(Function<Map<Session, StudentWsSession>, StudentWsSession> get) {
        return Optional.of(get.apply(sessions));
    }

    public static Optional<StudentWsSession> getSession(Session wsSession) {
        return Optional.of(sessions.get(sessions));
    }

    public static void removeSession(Session wsSession) {
        sessions.remove(wsSession);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.websocket.Session;

import entities.Lecture;
import entities.Teacher;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author development
 */
public class TeacherWsSession {

    @Getter
    private Teacher teacher;

    @Getter
    private Session wsSession;

    @Getter
    @Setter
    private Lecture lecture;

    public TeacherWsSession(Teacher teacher, Session wsSession) {
        this.teacher = teacher;
        this.wsSession = wsSession;

    }

    //==========================================================================
    private static final Map<Session, TeacherWsSession> sessions = new ConcurrentHashMap<>();

    public static void addSession(Session wsSession, TeacherWsSession theSession) {
        sessions.put(wsSession, theSession);
    }

    public static Optional<TeacherWsSession> getSession(Function<Map<Session, TeacherWsSession>, TeacherWsSession> get) {
        return Optional.of(get.apply(sessions));
    }

    public static void removeSession(Session wsSession) {
        sessions.remove(wsSession);
    }

}

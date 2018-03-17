/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import entities.Lecture;
import entities.Teacher;
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
public class TeacherWsSession {

    @Getter
    @Setter
    private Teacher teacher;

    @Getter
    @Setter
    private Session wsSession;

    @Getter
    @Setter
    private Lecture lecture;

    public TeacherWsSession(Teacher teacher, Session wsSession, Lecture lecture) {
        this.teacher = teacher;
        this.wsSession = wsSession;
        this.lecture = lecture;
    }

    private static final Map<Lecture, TeacherWsSession> sessions = new ConcurrentHashMap<>();

    public static void addSession(Lecture theLecture, TeacherWsSession theSession) {
        sessions.put(theLecture, theSession);
    }

    public static Optional<TeacherWsSession> getSession(Function<Map<Lecture, TeacherWsSession>, TeacherWsSession> get) {
        return Optional.of(get.apply(sessions));
    }

    public static void removeSession(Lecture theLecture) {
        sessions.remove(theLecture);
    }

}

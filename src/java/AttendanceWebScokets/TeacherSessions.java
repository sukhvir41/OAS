/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import com.google.gson.Gson;
import entities.Lecture;
import entities.Student;
import entities.Teacher;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import utility.Utils;

/**
 *
 * @author development
 */
public class TeacherSessions {

    private static final Gson gson = new Gson();
    private static final Map<Session, Teacher> sessions = new ConcurrentHashMap<>();

    public static void sendNotification(Lecture lecture, Student student) {

        try {
            Teacher teacher = Utils.getFromDB(session -> getTeacher(session, lecture))
                    .orElseThrow(Exception::new);

            sessions.entrySet()
                    .parallelStream()
                    .filter(entry -> entry.getValue().equals(teacher))
                    .findFirst();
                    //.ifPresent(theTeacher -> theTeacher.getClassRoom()); // have to send info to teacher
        } catch (Exception ex) {

        }
    }

    private static Teacher getTeacher(org.hibernate.Session session, Lecture thelecture) {
        Lecture lecture = (Lecture) session.get(Lecture.class, thelecture.getId());
        return lecture.getTeaching().getTeacher();
    }

    private static void pushNotificationToTeacher() {

    }
}

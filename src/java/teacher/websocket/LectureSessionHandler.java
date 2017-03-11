/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.websocket;

import entities.Attendance;
import entities.Lecture;
import entities.Student;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@ApplicationScoped
public class LectureSessionHandler {

    private Map<String, Session> sessions = new HashMap<>();

    public synchronized void removeSession(Session session) {
        Iterator iterator = sessions.keySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry item = (Map.Entry) iterator.next();
            if (((Session) item.getValue()).equals(session)) {
                sessions.remove(item.getKey());
                break;
            }
        }

    }

    public void sendMessage(String lectureId, Session session) {
        sessions.put(lectureId, session);
    }

    public void sendMessage(Message message) {
        org.hibernate.Session session = Utils.openSession();
        session.beginTransaction();
        Date now = new Date();
        Calendar future = Calendar.getInstance();

        try {
            Lecture lecture = (Lecture) session.get(Lecture.class, message.getLectureId());
            future.setTime(lecture.getDate());
            future.add(Calendar.HOUR, 3);
            if (now.after(lecture.getDate()) && now.before(future.getTime())) {
                Student student = (Student) session.get(Student.class, message.getStudentId());
                List<Attendance> attendances = session.createCriteria(Attendance.class)
                        .add(Restrictions.eq("lecture", lecture))
                        .add(Restrictions.eq("student", student))
                        .list();
                if (attendances.size() > 0) {
                    Attendance attendance = attendances.get(0);
                    attendance.setMarkedByTeacher(message.isMarkedByTeacher());
                    attendance.setAttended(message.isMark());
                    session.update(attendance);
                } else {
                    Attendance attendance = new Attendance(message.isMark(), lecture, student);
                    attendance.setMarkedByTeacher(message.isMarkedByTeacher());
                    session.save(attendance);
                }
                Session ses = sessions.get(message.getLectureId());
                if (ses != null) {
                    ses.getBasicRemote().sendText("true");
                }
            }
            Session ses = sessions.get(message.getLectureId());
            if (ses != null) {
                ses.getBasicRemote().sendText("false");
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
                session.close();
                Session ses = sessions.get(message.getLectureId());
                if (ses != null) {
                    ses.getBasicRemote().sendText("false");
                }
            } catch (IOException ex) {
            }
        }

    }

    public void sendMessageFromStudent(String lectureId, int studentId) {
        Session ses = sessions.get(lectureId);
        try {
            if (ses != null) {
                ses.getBasicRemote().sendText("" + studentId);
            }
        } catch (IOException e) {
        }
    }
}

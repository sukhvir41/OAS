/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.student;

import entities.ClassRoom;
import entities.Lecture;
import entities.Student;
import entities.Subject;
import entities.Teaching;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import utility.Controller;
import utility.Holder;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/attendance")
public class Attendance extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Student sessionStudent = (Student) req.getSession().getAttribute("student");
        Student student = (Student) session.get(Student.class, sessionStudent.getId());

        int totalLectures = 0;
        int totalAttendance = 0;
        List<Holder> holder = new ArrayList<>();
        for (Subject subject : student.getSubjects()) {
            //getting lectures of the class and subject
            List<Lecture> lectures = getLectures(student.getClassRoom(), subject, session);
            int lecturesCount = lectures.stream()
                    .mapToInt(e -> e.getCount())
                    .sum();
            totalLectures += lecturesCount;

            //getting student attendace according to the lectures and the subject 
            List<entities.Attendance> studentAttendances = getStudentAttendance(student, lectures, session);
            int attendanceCount = studentAttendances.stream()
                    .mapToInt(e -> e.getLecture().getCount())
                    .sum();
            totalAttendance += attendanceCount;
            Holder h = new Holder();
            h.setSubject(subject);
            h.setPresent(attendanceCount);
            h.setAbsent(lecturesCount - attendanceCount);
            holder.add(h);
        }

        req.setAttribute("holder", holder);
        req.setAttribute("totalpresent", totalAttendance);
        req.setAttribute("totalabsent", totalLectures - totalAttendance);

        req.getRequestDispatcher("/WEB-INF/student/attendance.jsp").include(req, resp);

    }

    private List<Lecture> getLectures(ClassRoom classRoom, Subject subject, Session session) {
        List<Teaching> teaching = session.createCriteria(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject))
                .list();

        if (teaching.size() > 0) {
            return session.createCriteria(Lecture.class)
                    .add(Restrictions.in("teaching", teaching))
                    .addOrder(Order.desc("date"))
                    .list();
        } else {
            return new ArrayList<>();

        }
    }

    /**
     * gets the list of attendance ie present attendance of the student for the
     * given lectures
     */
    private List<entities.Attendance> getStudentAttendance(Student student, List<Lecture> lectures, Session session) {
        if (lectures.size() > 0) {
            return session.createCriteria(entities.Attendance.class)
                    .add(Restrictions.in("lecture", lectures))
                    .add(Restrictions.eq("student", student))
                    .add(Restrictions.eq("attended", true))//  check is eqOrIsnull is requird or eq is fine
                    .list();

        } else {
            return new ArrayList<>();
        }
    }

}



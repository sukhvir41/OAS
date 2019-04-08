/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classteacher.postback;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entities.Attendance;
import entities.Lecture;
import entities.Student;
import entities.Teacher;
import entities.Teaching;
import utility.PostBackController;
import utility.Utils;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/classteacher/grantleavepost")
public class GrantLeavePost extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int studentId = Integer.parseInt(req.getParameter("studentId"));

        LocalDateTime startDate = Utils.getStartDate(req.getParameter("startdate"));
        LocalDateTime endDate = Utils.getEndDate(req.getParameter("enddate"));

        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());
        Student student = (Student) session.get(Student.class, studentId);
        if (student.getClassRoom().getId() == teacher.getClassRoom().getId()) {
            List<Teaching> teachings = session.createCriteria(Teaching.class)
                    .add(Restrictions.eq("classRoom", student.getClassRoom()))
                    .add(Restrictions.in("subject", student.getSubjects()))
                    .list();
            List<Lecture> lectures = null;
            if (!teachings.isEmpty()) {
                lectures = session.createCriteria(Lecture.class)
                        .add(Restrictions.in("teaching", teachings))
                        .add(Restrictions.between("date", startDate, endDate))
                        .list();
            }

            if (lectures != null && !lectures.isEmpty()) {
                lectures.stream()
                        .filter(lecture -> !checkAttendance(lecture, student, session))
                        .forEach(lecture -> session.save(new Attendance(lecture, student, true, true, true)));
            }
            resp.sendRedirect("/OAS/teacher/classteacher/detailstudent?studentId=" + studentId);
        } else {
            resp.sendRedirect("/OAS/accessdenied.jsp");
        }
    }

    private boolean checkAttendance(Lecture lecture, Student student, Session session) {
        List<Attendance> attendances = session.createCriteria(Attendance.class)
                .add(Restrictions.eq("lecture", lecture))
                .add(Restrictions.eq("student", student))
                .list();

        return attendances.size() > 0;

    }
}

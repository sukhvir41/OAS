/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Attendance;
import entities.Lecture;
import entities.Student;
import entities.Teaching;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/students/grantleavepost")
public class GrantLeavesPost extends PostBackController {
    // THIS WILL NOT WORK HABE TO FIX IT
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        Date start = new SimpleDateFormat("yyyy-mm-dd").parse(req.getParameter("startdate"));
        Date end = new SimpleDateFormat("yyyy-mm-dd").parse(req.getParameter("enddate"));
        Student student = (Student) session.get(Student.class, studentId);
        List<Teaching> teachings = session.createCriteria(Teaching.class)
                .add(Restrictions.eq("classRoom", student.getClassRoom()))
                .add(Restrictions.in("subject", student.getSubjects()))
                .list();
        List<Lecture> lectures = null;
        if (!teachings.isEmpty()) {
            lectures = session.createCriteria(Lecture.class)
                    .add(Restrictions.in("teaching", teachings))
                    .add(Restrictions.between("date", start, end))// this will not work
                    .list();
        }

        if (lectures != null && !lectures.isEmpty()) {
            lectures.stream()
                    .filter(lecture -> !checkAttendance(lecture, student, session))
                    .forEach(lecture -> session.save(new Attendance(true, lecture, student, true)));
        }
        resp.sendRedirect("/OAS/admin/students/detailstudent?studentId=" + studentId);
    }

    private boolean checkAttendance(Lecture lecture, Student student, Session session) {
        List<Attendance> attendances = session.createCriteria(Attendance.class)
                .add(Restrictions.eq("lecture", lecture))
                .add(Restrictions.eq("student", student))
                .list();

        return attendances.size() > 0 ? true : false;

    }

}

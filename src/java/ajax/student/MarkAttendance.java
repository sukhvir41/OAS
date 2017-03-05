/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.student;

import entities.Attendance;
import entities.Lecture;
import entities.Student;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.MacAddressUtil;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/ajax/markattendance")
public class MarkAttendance extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Session session = Utils.openSession();
        session.beginTransaction();

        try {
            String lectureId = req.getParameter("lectureId");
            Student student = (Student) req.getSession().getAttribute("student");
            student = (Student) session.get(Student.class, student.getId());
            Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
            MacAddressUtil util = new MacAddressUtil();
            String macId = util.getMacAddress(req.getRemoteAddr());
            if (macId.equals(student.getMacId())) {
                if (lecture.getTeaching().getClassRoom().equals(student.getClassRoom()) && student.getSubjects().contains(lecture.getTeaching().getSubject())) {
                    Attendance attendance = new Attendance(lecture, student);
                    attendance.setAttended(true);
                    attendance.setLeave(false);
                    attendance.setMarkedByTeacher(false);
                    session.save(attendance);
                    out.print("true");
                } else {
                    out.print("false");
                }
            } else {
                out.print("false");
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            out.print("error");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }

}

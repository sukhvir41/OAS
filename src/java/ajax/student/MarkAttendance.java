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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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
        Date date = new Date();
        try {
            String lectureId = req.getParameter("lectureId");
            Student student = (Student) req.getSession().getAttribute("student");
            student = (Student) session.get(Student.class, student.getId());
            Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);
            if (lecture.getTeaching().getClassRoom().equals(student.getClassRoom()) && student.getSubjects().contains(lecture.getTeaching().getSubject()) && !lecture.isEnded()) {
                Date lectureStartDate = lecture.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(lectureStartDate);
                cal.add(Calendar.MINUTE, 30);

                List<Attendance> attendanceList = session.createCriteria(Attendance.class)
                        .add(Restrictions.eq("lecture", lecture))
                        .add(Restrictions.eq("student", student))
                        .list();
                if (attendanceList.size() <= 0) {
                    if (date.after(lectureStartDate) && date.before(cal.getTime())) {
                        MacAddressUtil mac = new MacAddressUtil();
                        String macAddr = mac.getMacAddress(req.getRemoteAddr());
                        System.out.println(macAddr + "  " + student.getMacId());
                        if (macAddr.equals(student.getMacId())) {
                            Attendance attendance = new Attendance(lecture, student);
                            attendance.setAttended(true);
                            attendance.setLeave(false);
                            attendance.setMarkedByTeacher(false);
                            session.save(attendance);
                            out.print("true");
                        } else {
                            System.out.println("mac issuse");
                            out.print("false");
                        }
                    } else {
                        System.out.println("timeissue");
                        out.print("false");
                    }
                } else {
                    System.out.println("marked");
                    out.print("false");
                }

            } else {
                System.out.println("wrong classroom");
                out.print("false");
            }

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
            out.print("false");
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

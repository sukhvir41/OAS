/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacher;

import entities.Lecture;
import entities.Student;
import entities.Teacher;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/lectures/detaillecture")
public class DetailLecture extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        String lectureId = req.getParameter("lectureId");
        Lecture lecture = (Lecture) session.get(Lecture.class, lectureId);

        if (lecture.getTeaching().getTeacher().getId() == teacher.getId()) {
            List<Student> students = lecture.getTeaching()// this will contain all studentts first and then only absent students
                    .getClassRoom()
                    .getStudents()
                    .stream()
                    .filter(student -> student.getSubjects().contains(lecture.getTeaching().getSubject()))
                    .sorted()
                    .collect(Collectors.toList());

            req.setAttribute("total", students.size());// setting the total size first as the list will have only absent students later

            List<Student> present = lecture.getAttendance()
                    .stream()
                    .filter(attendance -> attendance.isAttended())
                    .map(attendance -> attendance.getStudent())
                    .collect(Collectors.toList());
            students.removeAll(present);//removing all present students from the students list

            Collections.sort(present);// sorting all students accordig to their to roll number

            req.setAttribute("present", present);
            req.setAttribute("absent", students);
            req.setAttribute("headcount", present.size());
            req.setAttribute("lecture", lecture);
            req.getRequestDispatcher("/WEB-INF/admin/detaillecture.jsp").include(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.ClassRoom;
import entities.Course;
import entities.Subject;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;
import static java.lang.System.out;
import javax.servlet.http.HttpSession;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/addsubject")
public class AddSubject extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String name = req.getParameter("subjectname");
        boolean elective = Boolean.parseBoolean(req.getParameter("elective"));
        long courseId = Long.parseLong(req.getParameter("course"));
        ArrayList<String> classRooms = new ArrayList<>(Arrays.asList(req.getParameterValues("classes")));

        Subject subject = new Subject(name, elective);
        session.save(subject);
        Course course = (Course) session.get(Course.class, courseId);
        subject.addCourse(course);
        classRooms.stream()
                .map(Long::parseLong)
                .map(e -> (ClassRoom) session.get(ClassRoom.class, e))
                .forEach(e -> e.addSubject(subject));

        String from = req.getParameter("from");
        if (from == null || from.equals("")) {
            resp.sendRedirect("/OAS/admin/subjects");
        } else {
            if (from.equals("classroom")) {
                resp.sendRedirect("/OAS/admin/classrooms/detailclassroom?classroomId=" + classRooms.get(0));
            } else {
                resp.sendRedirect("/OAS/admin/courses/detailcourse?courseId=" + courseId);
            }
        }

    }

}

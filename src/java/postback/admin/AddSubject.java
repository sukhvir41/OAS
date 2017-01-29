/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.ClassRoom;
import entities.Course;
import entities.Subject;
import java.io.IOException;

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

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/addsubject")
public class AddSubject extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("caled add subject");
        try {
            String name = req.getParameter("subjectname");
            boolean elective = Boolean.parseBoolean(req.getParameter("elective"));
            int courseId = Integer.parseInt(req.getParameter("course"));
            ArrayList<String> classRooms = new ArrayList<>(Arrays.asList(req.getParameterValues("classes")));
            Session session = Utils.openSession();
            session.beginTransaction();
            Subject subject = new Subject(name, elective);
            session.save(subject);
            Course course = (Course) session.get(Course.class, courseId);
            subject.addCourse(course);
            classRooms.stream()
                    .map(Integer::parseInt)
                    .map(e -> (ClassRoom) session.get(ClassRoom.class, e))
                    .forEach(e -> e.addSubject(subject));
            session.getTransaction().commit();
            session.close();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/error");
    }
    
}

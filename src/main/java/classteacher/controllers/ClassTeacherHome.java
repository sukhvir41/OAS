/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classteacher.controllers;

import entities.Student;
import entities.Teacher;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
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
@WebServlet("/teacher/classteacher")
public class ClassTeacherHome extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());
        req.setAttribute("classroom", teacher.getClassRoom());
        List<Student> students = teacher.getClassRoom().getStudents();
        Collections.sort(students);
        req.setAttribute("students",students);
        req.getRequestDispatcher("/WEB-INF/classteacher/classteacher.jsp").include(req, resp);

    }

}

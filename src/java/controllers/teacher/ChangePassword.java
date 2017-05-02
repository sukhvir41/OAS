/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacher;

import entities.Teacher;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = "/teacher/changepassword")
public class ChangePassword extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        req.setAttribute("teacher", teacher);
        req.getRequestDispatcher("/WEB-INF/teacher/teacherchangepassword.jsp").include(req, resp);
    }

}

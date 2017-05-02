/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacher;

import entities.Login;
import entities.Teacher;
import entities.UserType;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/myaccount")
public class MyAccount extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        Login login = (Login) session.createCriteria(Login.class)
                .add(Restrictions.eq("id", teacher.getId()))
                .add(Restrictions.eq("type", UserType.Teacher.toString()))
                .list()
                .get(0);
        
        req.setAttribute("username", login.getUsername());
        req.setAttribute("teacher", teacher);
        
        req.getRequestDispatcher("/WEB-INF/teacher/teachermyaccount.jsp").include(req, resp);
    }

}

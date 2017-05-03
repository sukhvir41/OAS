/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.teacher;

import entities.Login;
import entities.Student;
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
 * @author pc31
 */
@WebServlet(urlPatterns = "/teacher/students/detailstudent")
public class DetailStudent extends Controller{

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        
        Student student = (Student) session.get(Student.class,studentId);
        Login login = (Login) session.createCriteria(Login.class)
                    .add(Restrictions.eq("type", UserType.Student.toString()))
                    .add(Restrictions.eq("id", student.getId()))
                    .list()
                    .get(0);
        req.setAttribute("student", student);
        req.setAttribute("username", login.getUsername());
        
        req.getRequestDispatcher("/WEB-INF/teacher/detailstudent.jsp").include(req, resp);
    }
    
}

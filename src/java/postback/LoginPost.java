/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback;

import entities.Login;
import entities.Student;
import entities.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/loginpost")
public class LoginPost extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("rememberme");
        Session session = Utils.openSession();
        session.beginTransaction();
        Login login;
        if (Utils.regexMatch("/^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,6})?$/", username)) {
            Query query = session.createQuery("from Login where email = :email");
            query.setString("email", username);
            login = (Login) query.list().get(0);
        } else {
            login = (Login) session.get(Login.class, username);
        }
        if (login.checkPassword(password)) {
            switch (login.getType()) {
                case "student": {
                    Student student = (Student) session.get(Student.class, login.getId());
                    req.getSession().setAttribute("student", student);
                    req.getSession().setAttribute("type", "student");
                    break;
                }
                case "teacher": {
                    Teacher teacher = (Teacher) session.get(Teacher.class, login.getId());
                    req.getSession().setAttribute("teacher", teacher);
                    req.getSession().setAttribute("type", "teacher");
                    break;
                }
            }
            session.getTransaction().commit();
            session.close();
            
        } else {
            session.getTransaction().commit();
            session.close();
            resp.sendRedirect("login?verified=false");
        }
        
        req.getSession().setAttribute("valid", true);
        if (remember != null && !remember.equals("") && remember.equals(true)) {
            req.getSession().setMaxInactiveInterval(0);
        }
        switch (login.getType()) {
            case "student":
                resp.sendRedirect("student/home");
                break;
            
            case "teacher":
                resp.sendRedirect("teacher/home");
                break;
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }
    
}

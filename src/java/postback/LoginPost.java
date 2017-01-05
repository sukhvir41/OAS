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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession htppSession = req.getSession();
        Session session = Utils.openSession();
        session.beginTransaction();
        Login login = null;
        if (Utils.regexMatch("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", username, Pattern.CASE_INSENSITIVE)) {
            System.out.println("email worked");
            Query query = session.createQuery("from Login where email = :email");
            query.setString("email", username);
            login = (Login) query.list().get(0);
        } else {
            login = (Login) session.get(Login.class, username);
        }
        if (login != null) {
            if (login.checkPassword(password)) {
                switch (login.getType()) {
                    case "student": {
                        Student student = (Student) session.get(Student.class, login.getId());
                        htppSession.setAttribute("student", student);
                        htppSession.setAttribute("type", "student");
                        break;
                    }
                    case "teacher": {
                        Teacher teacher = (Teacher) session.get(Teacher.class, login.getId());
                        htppSession.setAttribute("teacher", teacher);
                        htppSession.setAttribute("type", "teacher");
                        break;
                    }
                    case "admin": {
                        htppSession.setAttribute("details", login);
                        htppSession.setAttribute("type", "admin");
                        break;
                    }
                }
                htppSession.setAttribute("accept", true);
                if (remember != null && remember.equals("true")) {
                    htppSession.setMaxInactiveInterval(864000);
                    String id = Utils.generateSessionId();
                    Cookie cookieSessionId = new Cookie("sid", id);
                    cookieSessionId.setMaxAge(864000);
                    login.setSessionId(id);
                    String token = Utils.createToken(12);
                    Cookie cookieSessionToken = new Cookie("stoken", token);
                    cookieSessionToken.setMaxAge(864000);
                    login.setSessionToken(token);
                    resp.addCookie(cookieSessionId);
                    resp.addCookie(cookieSessionToken);
                }
                switch (login.getType()) {
                    case "student":
                        resp.sendRedirect("student");
                        break;
                    
                    case "teacher":
                        resp.sendRedirect("teacher");
                        break;
                    case "admin":
                        resp.sendRedirect("admin");
                        break;
                }
            } else {
                resp.sendRedirect("login?verified=false");
            }
        } else {
            resp.sendRedirect("login?verified=false");
        }
        session.getTransaction().commit();
        session.close();
        System.out.println("called session closed");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }
    
}

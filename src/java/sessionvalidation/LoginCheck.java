/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionvalidation;

import entities.Login;
import entities.Student;
import entities.Teacher;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = {"/login", "/", "/login.jsp"})
public class LoginCheck implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession httpSession = req.getSession();

        try {
            if (httpSession.getAttribute("accept") != null) {
                if ((boolean) httpSession.getAttribute("accept") == true) {
                    switch ((String) httpSession.getAttribute("type")) {
                        case "students":
                            resp.sendRedirect("/OAS/student");
                            break;
                        case "teacher":
                            resp.sendRedirect("/OAS/teacher");
                            break;
                        case "admin":
                            resp.sendRedirect("/OAS/admin");
                            break;
                    }
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                System.out.println("called start cookie");
                Cookie id = null, token = null;

                for (Cookie cookie : req.getCookies()) {
                    switch (cookie.getName()) {
                        case "sid":
                            id = cookie;
                            break;
                        case "stoken":
                            token = cookie;
                            break;
                    }
                }
                if (id != null && token != null) {
                    Session session = Utils.openSession();
                    session.beginTransaction();
                    Login login = (Login) session.createQuery("from Login where sessionId = :id").setString("id", id.getValue()).list().get(0);
                    if (login.matchSessionToken(token.getValue())) {
                        httpSession.setAttribute("type", login.getType());
                        httpSession.setAttribute("accept", true);
                        httpSession.setAttribute("extenedCookie", true);
                        System.out.println("called end cookie");
                        switch ((String) httpSession.getAttribute("type")) {
                            case "students": {
                                Student student = (Student) session.get(Student.class, login.getId());
                                httpSession.setAttribute("student", student);
                                httpSession.setAttribute("type", "student");
                                resp.sendRedirect("/OAS/student");
                            }
                            break;
                            case "teacher": {
                                Teacher teacher = (Teacher) session.get(Teacher.class, login.getId());
                                httpSession.setAttribute("teacher", teacher);
                                httpSession.setAttribute("type", "teacher");
                                resp.sendRedirect("/OAS/teacher");
                            }
                            break;
                            case "admin": {
                                httpSession.setAttribute("admin", login);
                                httpSession.setAttribute("type", "admin");
                                resp.sendRedirect("/OAS/admin");
                            }
                            break;
                        }

                        session.getTransaction().commit();
                        session.close();
                    } else {
                        System.out.println("token not match");
                        chain.doFilter(request, response);
                    }
                } else {
                    chain.doFilter(request, response);
                }

            }
        } catch (Exception e) {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionvalidation;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Student;
import entities.UserType;

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = {"/student/*", "/student"})
public class StudentValidation implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp;
        HttpServletRequest req;
        HttpSession session;
        resp = (HttpServletResponse) response;
        req = (HttpServletRequest) request;
        session = req.getSession();

        try {
            if ((boolean) req.getServletContext().getAttribute("ready")) {
                if ((boolean) session.getAttribute("accept") && (session.getAttribute("type")).equals(UserType.Student)) {
                    Student student = (Student) session.getAttribute("student");
                    if (student.isVerified()) {

                        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                        resp.setDateHeader("Expires", 0);
                        chain.doFilter(request, response);
                    } else {
                        resp.sendRedirect("/OAS/notverified.jsp");
                    }
                } else {
                    resp.sendRedirect("/OAS/login");
                }
            }else{
                resp.sendRedirect("/OAS/accessdenied.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/OAS/login");
        }
    }

    @Override
    public void destroy() {

    }
}

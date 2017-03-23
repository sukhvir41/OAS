/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionvalidation;

import entities.Student;
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

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = {"/student/*", "/student"})
public class StudentValidation implements Filter {

    HttpServletResponse resp;
    HttpServletRequest req;
    HttpSession session;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        resp = (HttpServletResponse) response;
        req = (HttpServletRequest) request;
        session = req.getSession();

        try {

            if ((boolean) session.getAttribute("accept") && ((String) session.getAttribute("type")).equals("student")) {
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
        } catch (Exception e) {
            System.out.println("body error");
            e.printStackTrace();
            resp.sendRedirect("/OAS/login");
        }
    }

    @Override
    public void destroy() {

    }
}

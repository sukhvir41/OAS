/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionvalidation;

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

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = {"/teacher/*", "/teacher"})
public class TeacherValidation implements Filter {

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
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        resp.setDateHeader("Expires", 0);
        try {
            if ((boolean) session.getAttribute("accept") == true && session.getAttribute("type").equals("teacher")) {
                Teacher teacher = (Teacher) session.getAttribute("teacher");
                if (teacher.isVerified()) {
                    checkCookie();
                    chain.doFilter(request, response);
                } else {
                    resp.sendRedirect("/OAS/notverified.jsp");
                }
            } else {
                resp.sendRedirect("/OAS/login");
            }
        } catch (Exception e) {
            resp.sendRedirect("/OAS/login");
        }
    }

    @Override
    public void destroy() {

    }

    private void checkCookie() {
        try {
            if ((boolean) session.getAttribute("extenedCookie")) {
                session.setAttribute("extenedCookie", false);
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

                id.setMaxAge(864000);
                token.setMaxAge(864000);
                resp.addCookie(id);
                resp.addCookie(token);
            }

        } catch (Exception e) {
            System.out.println("error in extend cookie");

        }
    }

}

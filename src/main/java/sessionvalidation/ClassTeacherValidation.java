/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionvalidation;

import entities.Teacher;
import org.hibernate.Session;
import utility.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author sukhvir
 */
@WebFilter(urlPatterns = {"/teacher/class-teacher/*", "/teacher/class-teacher"})
public class ClassTeacherValidation implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            HttpSession ses = req.getSession();
            Teacher teacher = (Teacher) ses.getAttribute("teacher");
            teacher = (Teacher) session.get(Teacher.class, teacher.getId());
            if (teacher.getClassRoom() != null) {
                session.getTransaction().commit();
                session.close();
                chain.doFilter(request, response);
            } else {
                session.getTransaction().commit();
                session.close();
                resp.sendRedirect("/OAS/error");
            }

        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            resp.sendRedirect("/OAS/error");

        }
    }

    @Override
    public void destroy() {

    }

}

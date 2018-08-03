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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = {"/teacher/classteacher/*", "/teacher/classteacher"})
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionvalidation;

import entities.Teacher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = {"/teacher/hod", "/teacher/hod/*"})
public class HodValidation implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        try {
            HttpSession ses = req.getSession();
            Teacher teacher = (Teacher) ses.getAttribute("teacher");
            if (teacher.isHod()) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("/OAS/error");
            }
        } catch (Exception e) {
            resp.sendRedirect("/OAS/error");
        }
        
    }
    
    @Override
    public void destroy() {
        
    }
    
}

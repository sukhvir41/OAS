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

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = {"/login", "/"})
public class LoginCheck implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        try {
            if (session.getAttribute("accept") != null && (boolean) session.getAttribute("accept") == true) {
                switch ((String) session.getAttribute("type")) {
                    case "students":
                        resp.sendRedirect("/OAS/student");
                        break;
                    case "teacher":
                        resp.sendRedirect("/OAS/teacher");
                        break;
                    case "admin":
                        resp.sendRedirect("/OAS/administrator");
                        break;
                }
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}

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

import entities.Admin;
import entities.AdminType;

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = "/admin/admins/deleteadmin")
public class AdminDeleteValidation implements Filter {

    HttpServletResponse resp;
    HttpServletRequest req;
    HttpSession session;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            resp = (HttpServletResponse) response;
            req = (HttpServletRequest) request;
            session = req.getSession();
            if (((Admin) session.getAttribute("admin")).getType().equals(AdminType.Main.toString())) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("/OAS/error");//### make access denied page
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        }
    }

    @Override
    public void destroy() {

    }

}

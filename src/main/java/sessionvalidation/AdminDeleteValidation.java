/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionvalidation;

import entities.Admin;
import entities.AdminType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author sukhvir
 */
@WebFilter(urlPatterns = "/admin/admins/delete-admin")
public class AdminDeleteValidation implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        try {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validationfilters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sukhvir
 */
@WebFilter(urlPatterns = "/loginpost")
public class LoginValidation implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username.length()>=8 && username.length()<=20 && password.length()>=8 && password.length()<=40){
            chain.doFilter(request, response);
        }else{
            ((HttpServletResponse)response).sendRedirect("login?verified=false");
        }
    }

    @Override
    public void destroy() {

    }

}

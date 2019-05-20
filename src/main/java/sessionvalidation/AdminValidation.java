/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionvalidation;

import entities.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author sukhvir
 */
@WebFilter(urlPatterns = { "/admin/*", "/admin" })
public class AdminValidation implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp;
		HttpServletRequest req;
		HttpSession session;

		resp = (HttpServletResponse) response;
		req = (HttpServletRequest) request;
		session = req.getSession();

		try {
			if ( (boolean) session.getAttribute( "accept" ) && session.getAttribute( "type" )
					.equals( UserType.Admin ) ) {
				resp.setHeader( "Cache-Control", "no-cache, no-store, must-revalidate" ); // HTTP 1.1.
				resp.setHeader( "Pragma", "no-cache" ); // HTTP 1.0.
				resp.setDateHeader( "Expires", 0 );
				chain.doFilter( request, response );
			}
			else {
				resp.sendRedirect( "/OAS/login" );
			}
		}
		catch (Exception e) {
			System.out.println( "body error" );
			e.printStackTrace();
			resp.sendRedirect( "/OAS/login" );
		}
	}

	@Override
	public void destroy() {

	}
}

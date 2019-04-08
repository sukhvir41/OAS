package controllers;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import utility.Controller;

@WebServlet(urlPatterns = "/register")
public class Register extends Controller {
	@Override
	public void process(
			HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out)
			throws Exception {
		req.getRequestDispatcher( "/WEB-INF/register.jsp" )
				.include( req, resp );
	}
}

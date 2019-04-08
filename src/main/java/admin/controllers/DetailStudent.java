/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Student;
import utility.Controller;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/students/detailstudent")
public class DetailStudent extends Controller {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		long studentId = Integer.valueOf( req.getParameter( "studentId" ) );

		Student student = (Student) session.get( Student.class, studentId );

		req.setAttribute( "student", student );
		req.setAttribute( "username", student.getUser().getUsername() );

		req.getRequestDispatcher( "/WEB-INF/admin/detailstudent.jsp" ).include( req, resp );

	}

}

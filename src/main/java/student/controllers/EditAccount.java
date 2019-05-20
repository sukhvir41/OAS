/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.controllers;

import entities.Course;
import entities.Student;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/account/editstudent")
public class EditAccount extends Controller {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {
		Student student = (Student) req.getSession().getAttribute( "student" );
		student = (Student) session.get( Student.class, student.getId() );

		List<Course> courses = session.createCriteria( Course.class )
				.list();

		req.setAttribute( "username", student.getUser().getUsername() );
		req.setAttribute( "student", student );
		req.setAttribute( "fname", student.getFName() );
		req.setAttribute( "lname", student.getLName() );
		req.setAttribute( "courses", courses );
		req.getRequestDispatcher( "/WEB-INF/student/studenteditprofile.jsp" ).include( req, resp );
	}

}

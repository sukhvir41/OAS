/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.postback;

import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Department;
import entities.Teacher;
import utility.PostBackController;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/account/updateteacher")
public class UpdateAccount extends PostBackController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {
		Teacher sessionTeacher = (Teacher) httpSession.getAttribute( "teacher" );
		Teacher teacher = (Teacher) session.get( Teacher.class, sessionTeacher.getId() );

		String email = req.getParameter( "email" );
		String number = req.getParameter( "number" );
		String fName = req.getParameter( "fname" );
		String lName = req.getParameter( "lname" );

		if ( !teacher.getUser().getEmail().equals( email ) ) {

			teacher.getUser().setEmail( email );
			resp.sendRedirect( "/OAS/logout" );
		}
		else {
			resp.sendRedirect( "/OAS/account/editteacher" );
		}

		teacher.setVerified( false );
		teacher.setUnaccounted( false );

		teacher.getDepartment()
				.stream()
				.forEach( department -> department.getTeachers().remove( teacher ) );

		teacher.setDepartment( null );

		Arrays.asList( req.getParameterValues( "department" ) )
				.stream()
				.map( department -> Integer.parseInt( department ) )
				.map( department -> (Department) session.get( Department.class, department ) )
				.forEach( department -> department.addTeacher( teacher ) );

		teacher.setFName( fName );//adding
		teacher.getUser().setNumber( Long.parseLong( number ) );//adding
		teacher.setLName( lName );//adding
	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.ClassRoom;
import entities.Department;
import entities.Student;
import entities.Subject;
import entities.Teacher;
import org.apache.commons.lang3.StringUtils;
import utility.PostBackController;
import utility.UrlParameters;

import static java.util.Arrays.asList;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/registerpost")
public class Registeration extends PostBackController {


	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		String firstName = req.getParameter( "firstname" );
		String lastName = req.getParameter( "lastname" );
		String email = req.getParameter( "email" );
		String userName = req.getParameter( "username" );
		String password = req.getParameter( "password" );
		String numberString = req.getParameter( "number" );
		String type = req.getParameter( "type" );

		if ( StringUtils.isAnyBlank( firstName, lastName, email, userName, password, numberString, type ) ) {
			UrlParameters parameters = new UrlParameters()
					.addErrorParameter()
					.addParamter( "firstname", firstName )
					.addParamter( "lastname", lastName )
					.addParamter( "email", email )
					.addParamter( "username", userName )
					.addParamter( "number", numberString )
					.addMessage( "Please enter the required data" );
			req.setAttribute( "error", parameters );
			throw new Exception();
		}

		int rollNumber = 0;
		long classId = 0;
		List<String> subjects;
		List<String> departments;
		String hod = null;
		long number = Long.parseLong( numberString );

		if ( type.equals( "student" ) ) {
			subjects = Arrays.asList( req.getParameterValues( "subject" ) );
			classId = Long.parseLong( req.getParameter( "class" ) );
			rollNumber = Integer.parseInt( req.getParameter( "rollnumber" ) );

			ClassRoom classRoom = session.get( ClassRoom.class, classId );
			Student student = new Student( rollNumber, type, lastName, classRoom, userName, password, email, number );

			subjects.stream()
					.map( Long::parseLong )
					.map( subjectId -> session.get( Subject.class, subjectId ) )
					.forEachOrdered( student::addSubject );

			student.addClassRoom( classRoom );

			session.save( student );
		}
		else {
			departments = new ArrayList<>( asList( req.getParameterValues( "department" ) ) );

			Teacher teacher = new Teacher( firstName, lastName, userName, password, email, number );
			session.save( teacher );

			departments.stream()
					.map( Long::parseLong )
					.map( e -> (Department) session.get( Department.class, e ) )
					.forEachOrdered( teacher::addDepartment );
		}
		resp.sendRedirect( "login" );
	}

	@Override
	public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object object = req.getAttribute( "error" ); //error Url parameter sent from the process method
		if ( Objects.nonNull( object ) ) {
			resp.sendRedirect( ( (UrlParameters) object ).getUrl( "/OAS/register" ) );
		}
		else {
			resp.sendRedirect(
					new UrlParameters()
							.addErrorParameter()
							.addMessage( "Please enter the required data" )
							.getUrl( "/OAS/register" )
			);
		}
	}
}

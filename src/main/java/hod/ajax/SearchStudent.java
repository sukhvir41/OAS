/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.ClassRoom;
import entities.Department;
import entities.Student;
import entities.Subject;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/ajax/searchstudents")
public class SearchStudent extends AjaxController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {
		Department department = (Department) httpSession.getAttribute( "department" );
		department = (Department) session.get( Department.class, department.getId() );

		int classroomId = Integer.parseInt( req.getParameter( "classroom" ) );
		String subjectId = req.getParameter( "subject" );
		String filter = req.getParameter( "filter" );
		Gson gson = new Gson();
		JsonArray jsonStudents = new JsonArray();
		ClassRoom classRoom = (ClassRoom) session.get( ClassRoom.class, classroomId );

		if ( classRoom.getCourse().getDepartment().getId() == department.getId() ) {

			if ( subjectId.equals( "all" ) ) {

				if ( filter.equals( "all" ) ) {
					classRoom.getStudents()
							.stream()
							.filter( student -> !student.isUnaccounted() )
							.forEach( student -> add( student, jsonStudents ) );
				}
				else {
					classRoom.getStudents()
							.stream()
							.filter( student -> !student.isUnaccounted() )
							.filter( e -> e.isVerified() == Boolean.parseBoolean( filter ) )
							.forEach( e -> add( e, jsonStudents ) );
				}
			}
			else {

				Subject subject = (Subject) session.get( Subject.class, Integer.parseInt( subjectId ) );
				if ( filter.equals( "all" ) ) {
					classRoom.getStudents()
							.stream()
							.filter( student -> !student.isUnaccounted() )
							.filter( student -> student.getSubjects().contains( subject ) )
							.forEach( student -> add( student, jsonStudents ) );
				}
				else {
					classRoom.getStudents()
							.stream()
							.filter( student -> !student.isUnaccounted() )
							.filter( e -> e.isVerified() == Boolean.parseBoolean( filter ) )
							.filter( e -> e.getSubjects().contains( subject ) )
							.forEach( e -> add( e, jsonStudents ) );
				}

			}
			out.print( gson.toJson( jsonStudents ) );
		}
		else {

			out.print( "error" );
		}

	}

	private void add(Student student, JsonArray jsonStudents) {
		JsonObject studentJson = new JsonObject();
		studentJson.addProperty( "id", student.getId().toString() );
		studentJson.addProperty( "name", student.toString() );
		studentJson.addProperty( "email", student.getUser().getEmail() );
		studentJson.addProperty( "number", student.getUser().getNumber() );
		studentJson.addProperty(
				"classroom",
				student.getClassRoom().getName() + " " + student.getClassRoom().getDivision()
		);
		studentJson.addProperty( "rollnumber", student.getRollNumber() );
		studentJson.addProperty( "verified", student.isVerified() );
		studentJson.add( "subjects", addSubjects( student ) );
		jsonStudents.add( studentJson );
	}

	private JsonElement addSubjects(Student e) {
		JsonArray jsonSubjects = new JsonArray();
		e.getSubjects()
				.stream()
				.forEach( subject -> jsonSubjects.add( subject.getName() ) );
		return jsonSubjects;
	}
}

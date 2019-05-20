/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classteacher.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.Student;
import entities.Teacher;
import org.hibernate.Session;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import static utility.Constants.*;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/classteacher/ajax/getstudents")
public class GetStudents extends AjaxController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {
		String filter = req.getParameter( "filter" );

		Teacher teacher = (Teacher) httpSession.getAttribute( "teacher" );
		teacher = (Teacher) session.get( Teacher.class, teacher.getId() );

		Gson gson = new Gson();
		JsonArray jsonStudent = new JsonArray();

		if ( filter.equals( "all" ) ) {
			teacher.getClassRoom()
					.getStudents()
					.stream()
					.sorted()
					.forEach( student -> add( student, jsonStudent ) );

		}
		else {
			teacher.getClassRoom()
					.getStudents()
					.stream()
					.filter( student -> student.isVerified() == Boolean.parseBoolean( filter ) )
					.sorted()
					.forEach( student -> add( student, jsonStudent ) );
		}

		out.print( gson.toJson( jsonStudent ) );
	}

	private void add(Student student, JsonArray jsonStudents) {
		JsonObject studentJson = new JsonObject();

		studentJson.addProperty( ID, student.getId().toString() );
		studentJson.addProperty( NAME, student.toString() );
		studentJson.addProperty( EMAIL, student.getUser().getEmail() );
		studentJson.addProperty( NUMBER, student.getUser().getNumber() );
		studentJson.addProperty(
				CLASSROOM,
				student.getClassRoom().getName() + " " + student.getClassRoom().getDivision()
		);
		studentJson.addProperty( ROLLNUMBER, student.getRollNumber() );
		studentJson.addProperty( VERIFIED, student.isVerified() );
		studentJson.add( SUBJECTS, addSubjects( student ) );

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

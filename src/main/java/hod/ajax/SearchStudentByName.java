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
import entities.Department;
import entities.Student;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/hod/ajax/searchstudentbyname")
public class SearchStudentByName extends AjaxController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {
		String name = req.getParameter( "name" );

		Department department = (Department) httpSession.getAttribute( "department" );
		Department currentDepartment = (Department) session.get( Department.class, department.getId() );

		name = req.getParameter( "name" );
		List<Student> students = session.createCriteria( Student.class )
				.add( Restrictions.or(
						Restrictions.like( "fName", "%" + name + "%" ),
						Restrictions.like( "lName", "%" + name + "%" )
				) )
				.list();
		JsonArray jsonStudents = new JsonArray();
		Gson gson = new Gson();
		students.stream()
				.filter( student -> student.getClassRoom()
						.getCourse()
						.getDepartment()
						.getId() == currentDepartment.getId() )
				.forEach( student -> add( student, jsonStudents ) );
		out.print( gson.toJson( jsonStudents ) );
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
		//studentJson.addProperty( "verified", student.isVerified() );
		studentJson.add( "subjects", addSubjects( student ) );
		jsonStudents.add( studentJson );
	}

	private JsonElement addSubjects(Student e) {
		JsonArray jsonSubjects = new JsonArray();
		/*e.getSubjects()
				.stream()
				.forEach( subject -> jsonSubjects.add( subject.getName() ) );*/
		return jsonSubjects;
	}
}

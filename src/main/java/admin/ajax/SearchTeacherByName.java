/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.ajax;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.Department;
import entities.Teacher;
import utility.AjaxController;

import static utility.Constants.CLASSTEACHER;
import static utility.Constants.DEPARTMENTS;
import static utility.Constants.EMAIL;
import static utility.Constants.HOD;
import static utility.Constants.HODOF;
import static utility.Constants.ID;
import static utility.Constants.NAME;
import static utility.Constants.NUMBER;
import static utility.Constants.VERIFIED;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/teachers/searchteacherbyname")
public class SearchTeacherByName extends AjaxController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {
		String name;

		resp.setContentType( "application/json" );

		name = req.getParameter( "name" );

		List<Teacher> teachers = session.createCriteria( Teacher.class )
				.add( Restrictions.or(
						Restrictions.like( "fName", "%" + name + "%" ),
						Restrictions.like( "lName", "%" + name + "%" )
				) )
				.list();
		Gson gson = new Gson();
		JsonArray jsonTeachers = new JsonArray();
		teachers.stream()
				.forEach( teacher -> add( teacher, jsonTeachers ) );

		out.print( gson.toJson( jsonTeachers ) );

	}

	private void add(Teacher teacher, JsonArray jsonTeachers) {
		JsonObject teacherJson = new JsonObject();
		teacherJson.addProperty( ID, teacher.getId().toString() );
		teacherJson.addProperty( NAME, teacher.toString() );
		teacherJson.addProperty( NUMBER, teacher.getUser().getNumber() );
		teacherJson.addProperty( EMAIL, teacher.getUser().getEmail() );
		teacherJson.addProperty( HOD, teacher.isHod() );
		if ( teacher.isHod() ) {
			teacherJson.add( HODOF, addDepartment( teacher.getHodOf() ) );
		}
		else {
			teacherJson.addProperty( HODOF, "not hod" );
		}

		teacherJson.addProperty( CLASSTEACHER, teacher.getClassRoom() == null ? "" : teacher.getClassRoom().getName() );
		teacherJson.add( DEPARTMENTS, addDepartment( teacher.getDepartment() ) );
		teacherJson.addProperty( VERIFIED, teacher.isVerified() );
		jsonTeachers.add( teacherJson );

	}

	private JsonElement addDepartment(Collection<Department> departments) {
		JsonArray department = new JsonArray();
		departments.stream()
				.forEach( e -> department.add( e.getName() ) );
		return department;
	}

}

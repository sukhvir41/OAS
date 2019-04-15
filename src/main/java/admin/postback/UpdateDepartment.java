/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Department;
import entities.Teacher;
import org.apache.commons.lang3.StringUtils;
import utility.PostBackController;
import utility.UrlParameters;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/update-department")
public class UpdateDepartment extends PostBackController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		String theDepartmentId = req.getParameter( "departmentId" );
		String theTeacherId = req.getParameter( "teacherId" );
		String theDepartmentName = req.getParameter( "departmentName" );

		UrlParameters parameters = new UrlParameters();

		if ( StringUtils.isAnyBlank( theDepartmentId, theDepartmentName ) ) {
			parameters.addErrorParameter()
					.addMessage( "Unable to edit the department as details were missing" );
			resp.sendRedirect( parameters.getUrl( "/OAS/admin/departments" ) );
			return;
		}

		int departmentId = Integer.parseInt( theDepartmentId );
		Department department = Department.getDepartment( departmentId, session );

		if ( theTeacherId != null ) {
			Teacher teacher = (Teacher) session.get( Teacher.class, Integer.parseInt( theTeacherId ) );
			if ( department.getHod() == null ) {
				teacher.addHodOf( department );
			}
			else {
				Teacher tempTeacher = department.getHod();
				tempTeacher.getHodOf().remove( department );
				department.setHod( null );
				teacher.addHodOf( department );
				if ( tempTeacher.getHodOf()
						.isEmpty() ) { // check if the teacher is hod of asome deaprtment or not if not remove as hod
					tempTeacher.setHod( false );
				}
			}
		}
		department.setName( theDepartmentName );

		resp.sendRedirect(
				parameters.addSuccessParameter()
						.addMessage( theDepartmentName + " was updated" )
						.addParamter( "departmetnId", theDepartmentId )
						.getUrl( "/OAS/admin/departments/detail-department" )
		);
	}

}

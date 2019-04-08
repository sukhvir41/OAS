
package admin.postback;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Course;
import entities.Department;
import org.apache.commons.lang3.StringUtils;
import utility.PostBackController;
import utility.UrlParameters;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/add-course")
public class AddCourse extends PostBackController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		String departmentId = req.getParameter( "departmentId" );
		String courseName = req.getParameter( "courseName" );

		if ( StringUtils.isAnyBlank( departmentId, courseName ) ) {
			onError( req, resp );
			return;
		}


		long id = Long.parseLong( departmentId );

		Department department = session.get( Department.class, id );
		Course course = new Course( courseName );
		department.addCourse( course );
		session.save( course );


		if ( req.getParameter( "from" ) != null ) {
			resp.sendRedirect(
					new UrlParameters().addSuccessParameter()
							.addMessage( courseName + " was added to the department" )
							.addParamter( "departmentId", departmentId )
							.getUrl( "/OAS/admin/detail-department" )
			);

		}
		else {
			resp.sendRedirect(
					new UrlParameters().addSuccessParameter()
							.addMessage( courseName + " was added" )
							.getUrl( "/OAS/admin/courses" )
			);
		}

	}

	@Override
	public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UrlParameters parameters = new UrlParameters()
				.addErrorParameter()
				.addMessage( "unable to add course as the provided data was incorrect" );

		String from = req.getParameter( "from" );
		String departmentId = req.getParameter( "departmentId" );

		if ( StringUtils.isNoneBlank( from, departmentId ) ) {
			parameters.addParamter( "departmentId", departmentId );
			resp.sendRedirect( parameters.getUrl( "/OAS/admin/detail-department" ) );
		}
		else {
			resp.sendRedirect( parameters.getUrl( "/OAS/admin/courses" ) );
		}
	}
}

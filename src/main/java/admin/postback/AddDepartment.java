/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Department;
import utility.PostBackController;
import utility.UrlParameters;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/add-department-post")
public class AddDepartment extends PostBackController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		String name = req.getParameter( "departmentName" );
		Department department = new Department( name );

		session.save( department );

		resp.sendRedirect(
				new UrlParameters()
						.addSuccessParameter()
						.addMessage( name + " was added" )
						.getUrl( "/OAS/admin/departments" )
		);

	}

	@Override
	public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter( "departmentName" );
		resp.sendRedirect(
				new UrlParameters()
						.addErrorParameter()
						.addMessage( "Error occurred while adding department " + name )
						.getUrl( "/OAS/admin/departments" )
		);
	}
}

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

import entities.Admin;
import entities.AdminType;
import org.apache.commons.lang3.StringUtils;
import utility.PostBackController;
import utility.UrlParameters;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/admins/add-admin-post")
public class AddAdmin extends PostBackController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		String username = req.getParameter( "username" );
		String email = req.getParameter( "email" );
		String password = req.getParameter( "password" );

		UrlParameters parameters = new UrlParameters();

		if ( StringUtils.isAnyBlank( username, email, password ) ) {
			onError( req, resp );
			return;
		}

		Admin admin = new Admin( username, password, email, AdminType.Sub );

		session.save( admin );

		parameters.addSuccessParameter()
				.addMessage( StringUtils.joinWith( " ", username, "was added" ) );
		resp.sendRedirect( parameters.getUrl( "/OAS/admin/admins" ) );

	}

	@Override
	public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UrlParameters parameters = new UrlParameters()
				.addErrorParameter()
				.addMessage( "Please provide the right parameters" );
		resp.sendRedirect( parameters.getUrl( "/OAS/admin/admins" ) );
	}
}

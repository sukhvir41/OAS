/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Admin;
import entities.AdminType;
import org.apache.commons.lang3.StringUtils;
import utility.Controller;
import utility.UrlParameters;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/admins/deleteadminpost")
public class DeleteAdmin extends Controller {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {


		UUID adminId = UUID.fromString( req.getParameter( "adminId" ) );

		Admin admin = Admin.getUserAdmin( adminId, session );

		UrlParameters parameters = new UrlParameters();

		req.setAttribute( "username", admin.getUser().getUsername() );

		if ( admin.getType().equals( AdminType.Sub ) ) {
			session.delete( admin );
			parameters.addSuccessParameter()
					.addMessage( StringUtils.joinWith( " ", admin.getUser().getUsername(), " was deleted" ) );
		}
		else {
			parameters.addErrorParameter()
					.addMessage( StringUtils.joinWith( " ", admin.getUser().getUsername(), " can not be deleted" ) );
		}


		resp.sendRedirect( parameters.getUrl( "/OAS/admin/admins" ) );

	}

	@Override
	public boolean showErrorLog() {
		return true;
	}

	@Override
	public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = (String) req.getAttribute( "username" );

		resp.sendRedirect(
				new UrlParameters()
						.addErrorParameter()
						.addMessage( username + " unable to delete the admin" )
						.getUrl( "/OAS/admin/admins" )
		);
	}
}

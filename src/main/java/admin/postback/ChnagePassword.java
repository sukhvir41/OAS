/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Admin;
import entities.UserType;
import org.hibernate.Session;
import utility.PostBackController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/myaccount/changepasswordpost")
public class ChnagePassword extends PostBackController {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		String oldPassword = req.getParameter( "oldpassword" );
		String newPassword = req.getParameter( "newpassword" );
		String renewPassword = req.getParameter( "renewpassword" );

		Admin admin = (Admin) httpSession.getAttribute( UserType.Admin.toString() );
		admin = (Admin) session.get( Admin.class, admin.getId() );
		if ( admin.getUser().checkPassword( oldPassword ) ) {
			if ( newPassword.length() >= 8 && newPassword.length() <= 40 && newPassword.equals( renewPassword ) ) {
				admin.getUser().setPassword( newPassword );
				session.update( admin );

				req.getSession().setAttribute( UserType.Admin.toString(), admin );
				resp.sendRedirect( "/OAS/admin/myaccount/changepassword?success=true" );
			}
			else {
				resp.sendRedirect( "/OAS/admin/myaccount/changepassword?error=true" );
			}
		}
		else {
			resp.sendRedirect( "/OAS/admin/myaccount/changepassword?error=true" );
		}

	}

}

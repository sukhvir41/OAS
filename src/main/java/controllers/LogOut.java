/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import entities.UserType;
import org.hibernate.Session;
import utility.Controller;
import utility.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/logout")
public class LogOut extends Controller {

	// logs out the user by invalidating its htt session , decrementing the user counter and invalidating the cookies and their respective data in the database
	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		for ( Cookie c : req.getCookies() ) {
			if ( c.getName().equals( "sessionId" ) ) {
				c.setMaxAge( 0 );
				resp.addCookie( c );
			}
			else if ( c.getName().equals( "sessionToken" ) ) {
				c.setMaxAge( 0 );
				resp.addCookie( c );
			}
		}

		UserType userType = (UserType) httpSession.getAttribute( "type" );
		userType.decrementCount();
		UUID userId = (UUID) httpSession.getAttribute( "userId" );

		User user = session.get( User.class, userId );

		user.setSessionId( null );
		user.setSessionToken( Utils.generateBase64());

		httpSession.removeAttribute( "accept" );
		httpSession.invalidate();

		resp.sendRedirect( "/OAS/login" );

	}

}

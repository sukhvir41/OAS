/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import AttendanceServices.MacHandlers;
import entities.Admin;
import entities.UserType;
import org.apache.commons.lang3.ObjectUtils;
import utility.Controller;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin")
public class AdminHome extends Controller {

	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		req.setAttribute( UserType.Student.toString(), UserType.Student.getCount() );
		req.setAttribute( UserType.Admin.toString(), UserType.Admin.getCount() );
		req.setAttribute( UserType.Teacher.toString(), UserType.Student.getCount() );

		req.setAttribute( "handlerReady", MacHandlers.isHandlerReady() );
		req.setAttribute( "details", MacHandlers.getDetails() );

		UUID id = (UUID) httpSession.getAttribute( "userId" );

		Admin theAdmin = Admin.getUserAdminReadOnly( id, session );

		httpSession.setAttribute( UserType.Admin.getType().toLowerCase(), theAdmin );

		req.getRequestDispatcher( "WEB-INF/admin/home.jsp" )
				.include( req, resp );


		extendCookie( req, resp );
	}

	private void extendCookie(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		try {
			if ( (boolean) session.getAttribute( "extenedCookie" ) ) {
				session.setAttribute( "extenedCookie", false );
				Cookie id = null, token = null;

				for ( Cookie cookie : req.getCookies() ) {
					switch ( cookie.getName() ) {
						case "sid":
							id = cookie;
							break;
						case "stoken":
							token = cookie;
							break;
					}
				}
				if ( ObjectUtils.allNotNull( id, token ) ) {
					id.setMaxAge( 864000 );
					token.setMaxAge( 864000 );
					resp.addCookie( id );
					resp.addCookie( token );
				}
			}

		}
		catch (Exception e) {
			System.out.println( "could not extend login session cookies" );
		}
	}
}

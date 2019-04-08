/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.jpa.QueryHints;

import entities.User;
import entities.UserType;
import entities.User_;
import org.apache.commons.lang3.ObjectUtils;
import utility.Controller;

/**
 * @author sukhvir
 */
@WebServlet({ "/login", })
public class Login extends Controller {

	// checks for if the user is logged in or not and i f hte user has redirects the user to the their respective home directory
	// else checks for their cookies for the respective tokens and validates to forwards the user to the same. IF all of he above fails the users sees the login page
	@Override
	public void process(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession,
			PrintWriter out) throws Exception {

		if ( Objects.nonNull( httpSession.getAttribute( "accept" ) ) ) {
			if ( (boolean) httpSession.getAttribute( "accept" ) ) {
				UserType user = (UserType) httpSession.getAttribute( "type" );
				resp.sendRedirect( "/OAS/" + user.getHomeLink() );
			}
		}
		else {
			checkCookies( req, resp, session, httpSession );
		}
	}

	// this redirects the user to the the login page bu including the page.
	@Override
	public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader( "Cache-Control", "no-cache, no-store, must-revalidate" ); // HTTP 1.1.
		resp.setHeader( "Pragma", "no-cache" ); // HTTP 1.0.
		resp.setDateHeader( "Expires", 0 );
		req.getRequestDispatcher( "/WEB-INF/login.jsp" )
				.include( req, resp );
	}


	// this methods checks for the required cookies the user has and validates it. if true redirects the user to its respective
	// home page
	private void checkCookies(
			HttpServletRequest req,
			HttpServletResponse resp,
			Session session,
			HttpSession httpSession) throws Exception {
		Cookie id = null, token = null;
		for ( Cookie cookie : req.getCookies() ) {
			switch ( cookie.getName() ) {
				case "sessionId":
					id = cookie;
					break;
				case "sessionToken":
					token = cookie;
					break;
			}
		}

		if ( ObjectUtils.allNotNull( id, token ) ) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery( User.class );
			Root<User> user = query.from( User.class );
			query.where(
					builder.equal( user.get( User_.sessionId ), id.getValue() )
			);

			User theUser = session.createQuery( query )
					.setHint( QueryHints.HINT_READONLY, true )
					.getSingleResult();


			if ( theUser.matchSessionToken( token.getValue() ) ) {
				forward( req, resp, theUser, session, httpSession );
			}
			else {
				onError( req, resp );
			}
		}
		else {
			onError( req, resp );
		}
	}

	private void forward(
			HttpServletRequest req,
			HttpServletResponse resp,
			User user,
			Session session,
			HttpSession httpSession) throws Exception {

		httpSession.setAttribute( "accept", true );
		httpSession.setAttribute( "extendCookie", true );
		httpSession.setAttribute( "type", user.getUserType() );
		httpSession.setAttribute( "userId", user.getId() );

		// incrementing the user count to keep track who has logged in
		user.getUserType()
				.incrementCount();

		resp.sendRedirect( "/OAS/" + user.getUserType().getHomeLink() );

	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.EntityHelper;
import entities.User;
import entities.UserType;
import entities.User_;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import utility.Controller;
import utility.Utils;

import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

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


        Stream.of(req.getCookies())
                .filter(cookie -> cookie.getName().equals("sessionId") || cookie.getName().equals("sessionToken"))
                .peek(cookie -> cookie.setMaxAge(0))
                .forEach(resp::addCookie);

        UserType userType = (UserType) httpSession.getAttribute("type");
        userType.decrementCount();
        UUID userId = (UUID) httpSession.getAttribute("userId");

        var builder = session.getCriteriaBuilder();
        var updateQuery = builder.createCriteriaUpdate(User.class);
        var root = updateQuery.from(User.class);

        updateQuery.set(User_.sessionId, (String) null);
        updateQuery.set(User_.sessionToken, Utils.generateBase64());

        updateQuery.where(builder.equal(root.get(User_.id), userId));

        session.createQuery(updateQuery)
                .executeUpdate();

        httpSession.removeAttribute("accept");
        httpSession.invalidate();

        resp.sendRedirect("/OAS/login");

    }

}

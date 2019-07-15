/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import entities.User_;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.jpa.QueryHints;
import utility.Controller;
import utility.Utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.time.LocalDateTime;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/resetPassword")
public class ResetPassword extends Controller {


    // this servlet takes the username of the the user and the token as the parameter nad validates it by checking
    // if the token belongs the user or not and if it does redirects to resetPassword page else to the expired if the link
    // to old or to the error if the token does not matches.
    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {

        String username = req.getParameter("username"); // username of the user
        String token = URLDecoder.decode(
                req.getParameter("token"),
                "UTF-8"
        ); // the token set for the user to reset the password

        if (StringUtils.isNoneBlank(username, token)) {
            LocalDateTime presentTime = LocalDateTime.now();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> user = query.from(User.class);

            query.where(
                    builder.equal(user.get(User_.username), username)
            );

            User theUser = session.createQuery(query)
                    .setHint(QueryHints.HINT_READONLY, true)
                    .getSingleResult();

            if (!theUser.isUsed()) {// checking if the token is used before or not

                if (token.equals(theUser.getToken())) {

                    LocalDateTime endTime = theUser.getForgotPasswordExpiryDate().get().plusMinutes(30);

                    if (presentTime.isAfter(theUser.getForgotPasswordExpiryDate().get()) && presentTime.isBefore(endTime)) {

                        req.setAttribute("username", theUser.getUsername());
                        //secret token used in the hidden tag that will be used in the post to validate the request
                        req.setAttribute("token", Utils.createToken(20));
                        req.getRequestDispatcher("WEB-INF/resetPassword.jsp")
                                .include(req, resp);
                    } else {
                        resp.sendRedirect("expired");
                    }
                } else {
                    resp.sendRedirect("error");
                }
            } else {
                resp.sendRedirect("error");
            }
        } else {
            resp.sendRedirect("error");
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback;

import entities.User;
import entities.User_;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlParameters;
import utility.Utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/login-post")
public class LoginPost extends PostBackController {

    @Override
    public void process(HttpServletRequest req,
                        HttpServletResponse resp,
                        Session session,
                        HttpSession httpSession,
                        PrintWriter out) throws Exception {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("rememberMe");

        UrlParameters parameters = new UrlParameters();

        if (StringUtils.isAnyBlank(username, password)) {
            parameters.addErrorParameter()
                    .addMessage("Username or Password is empty");
            resp.sendRedirect(parameters.getUrl("/OAS/login"));
            return;
        }

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        if (Utils.regexMatch("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", username, Pattern.CASE_INSENSITIVE)) {
            //username variable contains email
            query.where(
                    builder.equal(userRoot.get(User_.email), username)
            );
        } else {
            //username variable contains the username
            query.where(
                    builder.equal(userRoot.get(User_.username), username)
            );
        }

        User user = session.createQuery(query)
                .getSingleResult();

        if (user.checkPassword(password)) {
            httpSession.setAttribute("userId", user.getId());
            httpSession.setAttribute("type", user.getUserType());
            httpSession.setAttribute("accept", true);

            user.getUserType()
                    .incrementCount();

            if (Objects.equals(remember, "true")) {
                // setting the session to expire at 30 minutes
                httpSession.setMaxInactiveInterval(1800);

                String id = Utils.generateSessionId(session);
                Cookie cookieSessionId = new Cookie("sessionId", id);
                //keeping the cookie valid for 10 days
                cookieSessionId.setMaxAge(864000);
                user.setSessionId(id);

                String token = Utils.createToken(20);
                Cookie cookieSessionToken = new Cookie("sessionToken", token);
                //keeping the cookie valid for 10 days
                cookieSessionToken.setMaxAge(864000);
                user.setSessionToken(token);

                resp.addCookie(cookieSessionId);
                resp.addCookie(cookieSessionToken);
            }
            resp.sendRedirect("/OAS/" + user.getUserType().getHomeLink());

        } else {
            onError(req, resp);
        }
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(new UrlParameters().addErrorParameter()
                .addMessage("The username and password combination is wrong")
                .getUrl("/OAS/login")
        );
    }

}

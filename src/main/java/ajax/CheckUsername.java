/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.CriteriaHolder;
import entities.User;
import entities.User_;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 * <p>
 * if username from user table does not exiat return true
 */
@WebServlet(urlPatterns = "/ajax/check-username")
public class CheckUsername extends AjaxController {

    private static final String IS_UNIQUE = "isUnique";

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String username = req.getParameter("username");

        if (StringUtils.isBlank(username)) {
            onError(req, resp);
            return;
        }

        Gson gson = new Gson();
        JsonObject status = getSuccessJson();

        var holder = CriteriaHolder.getQueryHolder(session, Long.class, User.class);

        holder.getQuery()
                .where(
                        holder.getBuilder().equal(holder.getRoot().get(User_.username), username)
                );

        holder.getQuery()
                .select(holder.getBuilder().count(holder.getRoot().get(User_.username)));

        Long count = session.createQuery(holder.getQuery())
                .setMaxResults(1)
                .setReadOnly(true)
                .getSingleResult();

        if (count > 0) {
            status.addProperty(IS_UNIQUE, false);
        } else {
            status.addProperty(IS_UNIQUE, true);
        }

        out.println(gson.toJson(status));
    }
}


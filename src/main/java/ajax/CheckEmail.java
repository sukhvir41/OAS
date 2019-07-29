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
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;
import utility.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 * <p>
 * checks for unique email of the user if so return true else false. checks the user class
 */
@WebServlet(urlPatterns = "/ajax/check-email")
public class CheckEmail extends AjaxController {

    private static final String STATUS = "status";

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String email = req.getParameter("email");
        Gson gson = new Gson();
        JsonObject status = new JsonObject();

        if (StringUtils.isBlank(email)) {
            status.addProperty(STATUS, false);
            out.println(gson.toJson(status));
            return;
        }

        if (!Utils.isEamil(email)) {
            status.addProperty(STATUS, false);
            out.println(gson.toJson(status));
            return;
        }

        var holder = CriteriaHolder.getQueryHolder(session, Long.class, User.class);

        holder.getQuery()
                .where(
                        holder.getBuilder().equal(holder.getRoot().get(User_.email), email)
                );

        holder.getQuery().select(holder.getBuilder().count(holder.getRoot().get(User_.email)));

        Long count = session.createQuery(holder.getQuery())
                .setMaxResults(1)
                .setReadOnly(true)
                .getSingleResult();

        if (count > 0) {
            status.addProperty(STATUS, false);
        } else {
            status.addProperty(STATUS, true);
        }

        out.println(gson.toJson(status));
    }

}

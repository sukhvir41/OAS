/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import entities.User;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import utility.AjaxController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/checkusername")
public class CheckUsername extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String username = req.getParameter("username");

        int resultCount = (int) session.createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .setProjection(Projections.sum("username"))
                .uniqueResult();

        if (resultCount <= 0) {
            out.print(Boolean.TRUE);
        } else {
            out.print(Boolean.FALSE);
        }

    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Subject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlParameters;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/edit-subject")
public class EditSubject extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var subjectIdString = req.getParameter("subjectId");

        if (StringUtils.isBlank(subjectIdString)) {
            resp.sendRedirect(
                    new UrlParameters()
                            .addErrorParameter()
                            .addMessage("The subject you are trying to access does not exist")
                            .getUrl("/OAS/admin/subjects")
            );
            return;
        }


        long subjectId = Long.parseLong(subjectIdString);

        Subject subject = session.get(Subject.class, subjectId);
        req.setAttribute("subject", subject);

        req.getRequestDispatcher("/WEB-INF/admin/edit-subject.jsp")
                .include(req, resp);
    }

}

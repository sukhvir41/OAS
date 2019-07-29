/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlParameters;

import javax.persistence.criteria.CriteriaUpdate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/students/suspend-student")
public class SuspendStudent extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var studentIdString = req.getParameter("studentId");

        var urlParameters = new UrlParameters();

        if (StringUtils.isBlank(studentIdString)) {
            urlParameters.addErrorParameter()
                    .addMessage("The student account you are trying to suspend is does not exist");

            resp.sendRedirect(urlParameters.getUrl("/OAS/admin/students"));
            return;
        }

        UUID studentId = UUID.fromString(studentIdString);
        int updated = EntityHelper.updateInstances(session, User.class, jpaObjects -> updateStudent(jpaObjects, studentId));

        if (updated > 0) {
            urlParameters.addSuccessParameter()
                    .addMessage("The student account is suspended")
                    .addParameter("studentId", studentId);

            resp.sendRedirect(urlParameters.getUrl("/OAS/admin/students/student-details"));
        } else {
            urlParameters.addErrorParameter()
                    .addMessage("The student account you are trying to suspend is does not exist");
            resp.sendRedirect(urlParameters.getUrl("/OAS/admin/students"));
        }
    }

    private void updateStudent(CriteriaHolder<CriteriaUpdate<User>, User,User> jpaObjects, UUID studentId) {

        jpaObjects.getQuery().where(
                jpaObjects.getCriteriaBuilder().equal(jpaObjects.getRoot().get(User_.id), studentId)
        );

        jpaObjects.getQuery()
                .set(User_.status, UserStatus.SUSPENDED);

    }

}

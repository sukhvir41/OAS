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
import utility.UrlBuilder;

import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/students/activate-student")
public class ActivateStudent extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var params = new UrlBuilder();

        var studentIdString = req.getParameter("studentId");

        if (StringUtils.isBlank(studentIdString)) {
            params.addErrorParameter()
                    .addMessage("The student you are trying to access does not exists");

            resp.sendRedirect(params.getUrl("/OAS/admin/students"));
            return;
        }

        UUID studentId = UUID.fromString(studentIdString);

        int updatedCount = EntityHelper.updateInstances(session, User.class, jpaObjects -> updateStudentQuery(jpaObjects, studentId));

        if (updatedCount < 1) {
            // no rows updated
            params.addErrorParameter()
                    .addMessage("Either student does not exist or the student is unaccounted. Please remove the unaccounted status before activating");
        } else {
            // student is activated
            params.addSuccessParameter()
                    .addMessage("The student is activated")
                    .addParameter("studentId", studentId);

        }

        resp.sendRedirect(params.getUrl("/OAS/admin/students/student-details"));
    }


    private void updateStudentQuery(CriteriaHolder<User, CriteriaUpdate<User>, User> jpaObjects, UUID studentId) {
        Predicate predicate = jpaObjects.getCriteriaBuilder()
                .and(
                        jpaObjects.getCriteriaBuilder()
                                .equal(jpaObjects.getRoot().get(User_.id), studentId)
                );

        jpaObjects.getQuery()
                .set(User_.status, UserStatus.ACTIVE)
                .where(predicate);
    }
}

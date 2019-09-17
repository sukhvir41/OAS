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
import utility.CriteriaHolder;
import utility.UrlBuilder;

import javax.persistence.criteria.CriteriaUpdate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers/suspend-teacher")
public class SuspendTeacher extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var teacherIdString = req.getParameter("teacherId");

        var urlParameters = new UrlBuilder();

        if (StringUtils.isBlank(teacherIdString)) {
            urlParameters.addErrorParameter()
                    .addMessage("The student account you are trying to suspend is does not exist");

            resp.sendRedirect(urlParameters.getUrl("/OAS/admin/teachers"));
            return;
        }

        UUID teacherId = UUID.fromString(teacherIdString);
        int updated = EntityHelper.updateInstances(session, User.class, jpaObjects -> updateTeacher(jpaObjects, teacherId));

        if (updated > 0) {
            urlParameters.addSuccessParameter()
                    .addMessage("The student account is suspended")
                    .addParameter("studentId", teacherId);

            resp.sendRedirect(urlParameters.getUrl("/OAS/admin/students/student-details"));
        } else {
            urlParameters.addErrorParameter()
                    .addMessage("The student account you are trying to suspend is does not exist");
            resp.sendRedirect(urlParameters.getUrl("/OAS/admin/students"));
        }
    }

    private void updateTeacher(CriteriaHolder<CriteriaUpdate<User>, User> jpaObjects, UUID teacherId) {

        jpaObjects.getQuery().where(
                jpaObjects.getCriteriaBuilder().equal(jpaObjects.getRoot().get(User_.id), teacherId)
        );

        jpaObjects.getQuery()
                .set(User_.status, UserStatus.SUSPENDED);

    }
}

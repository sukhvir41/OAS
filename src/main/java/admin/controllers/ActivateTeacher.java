/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlParameters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers/activate-teacher")
public class ActivateTeacher extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var params = new UrlParameters();

        String teacherIdString = req.getParameter("teacherId");

        if (StringUtils.isBlank(teacherIdString)) {
            params.addErrorParameter()
                    .addMessage("The teacher you are trying to access does not exist");

            resp.sendRedirect(params.getUrl("/OAS/admin/teachers"));
            return;
        }

        UUID teacherId = UUID.fromString(teacherIdString);

        int updatedCount = EntityHelper.upadteInstances(session, Teacher.class, jpaObjects -> updateTeacherQuery(jpaObjects, teacherId));


        if (updatedCount < 1) {
            // no rows updated
            params.addErrorParameter()
                    .addMessage("Either teacher does not exist or the teacher is unaccounted. Please remove the unaccounted status before activating");
        } else {
            // student is activated
            params.addSuccessParameter()
                    .addMessage("The teacher is activated")
                    .addParameter("teacherId", teacherId);
        }

        resp.sendRedirect(params.getUrl("/OAS/admin/teacher/teacher-details"));
    }

    private void updateTeacherQuery(Triple<CriteriaBuilder, CriteriaUpdate<Teacher>, Root<Teacher>> jpaObjects, UUID teacherId) {
        Predicate predicate = jpaObjects.getLeft()
                .and(
                        jpaObjects.getLeft().equal(jpaObjects.getRight().get(Teacher_.id), teacherId),
                        jpaObjects.getLeft().equal(jpaObjects.getRight().get(Teacher_.unaccounted), false)
                );

        jpaObjects.getMiddle().set(Teacher_.verified, true)
                .where(predicate);
    }

}

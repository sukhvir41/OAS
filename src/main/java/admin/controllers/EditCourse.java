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

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/edit-course")
public class EditCourse extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var courseIdString = req.getParameter("courseId");

        if (StringUtils.isBlank(courseIdString)) {
            resp.sendRedirect(
                    new UrlBuilder().addErrorParameter()
                            .addMessage("The course you are trying to access does not exist")
                            .getUrl("/OAS/admin/courses")
            );
            return;
        }

        long courseId = Long.parseLong(courseIdString);

        Course course = EntityHelper.getInstance(courseId, Course_.id, Course.class, session, true, Course_.DEPARTMENT);

        var departments = getDepartments(course.getDepartment(), session);

        departments.add(0, course.getDepartment());

        req.setAttribute("course", course);
        req.setAttribute("departments", departments);
        req.setAttribute("transferCourses", getTransferCourses(course, session));

        req.getRequestDispatcher("/WEB-INF/admin/edit-course.jsp")
                .include(req, resp);

    }

    private List<Department> getDepartments(Department departmentToExclude, Session session) {
        var holder = CriteriaHolder.getQueryHolder(session, Department.class);

        holder.getQuery()
                .where(
                        holder.getBuilder()
                                .notEqual(holder.getRoot().get(Department_.id), departmentToExclude.getId())
                )
                .orderBy(
                        holder.getBuilder()
                                .asc(holder.getRoot().get(Department_.name))
                );

        return session.createQuery(holder.getQuery())
                .setReadOnly(true)
                .getResultList();
    }

    private List<Course> getTransferCourses(Course course, Session session) {
        var holder = CriteriaHolder.getQueryHolder(session, Course.class);

        holder.getQuery()
                .where(
                        holder.getBuilder()
                                .notEqual(holder.getRoot().get(Course_.id), course.getId()),
                        holder.getBuilder()
                                .equal(holder.getRoot().get(Course_.department), course.getDepartment())
                )
                .orderBy(
                        holder.getBuilder()
                                .asc(holder.getRoot().get(Course_.name))
                );

        return session.createQuery(holder.getQuery())
                .setReadOnly(true)
                .getResultList();
    }
}

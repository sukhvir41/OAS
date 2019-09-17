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

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/department-details")
public class DepartmentDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession,
                        PrintWriter out) throws Exception {

        var departmentIdString = req.getParameter("departmentId");

        if (StringUtils.isBlank(departmentIdString)) {
            resp.sendRedirect(
                    new UrlBuilder().addErrorParameter()
                            .addMessage("The department you are trying to access does not exist")
                            .getUrl("/OAS/admin/departments")
            );
            return;
        }

        long departmentID = Long.parseLong(departmentIdString);

        Department department = EntityHelper
                .getInstance(departmentID, Department_.id, Department.class, session, true, Department_.HOD);

        Teacher hod = department.getHod();

        req.setAttribute("canDelete", canDelete(department, session));
        req.setAttribute("department", department);
        req.setAttribute("hod", hod);
        req.getRequestDispatcher("/WEB-INF/admin/department-details.jsp")
                .include(req, resp);

    }

    // todo: check if this can be replaced with jooq or queryDSL (preferably jooq) to simplify it using union all
    private boolean canDelete(Department department, Session session) {
        if (getCourseCount(department, session) > 0) {
            return false;
        } else {
            return getTeacherCount(department, session) == 0;
        }
    }

    protected Long getTeacherCount(Department department, Session session) {

        var holder = CriteriaHolder.getQueryHolder(session, Long.class, TeacherDepartmentLink.class);
        holder.getQuery()
                .where(
                        holder.getBuilder()
                                .equal(holder.getRoot().get(TeacherDepartmentLink_.department), department)
                );

        holder.getQuery()
                .select(
                        holder.getBuilder()
                                .count(holder.getRoot().get(TeacherDepartmentLink_.teacher))
                );

        return session.createQuery(holder.getQuery())
                .setReadOnly(true)
                .setMaxResults(1)
                .getSingleResult();
    }

    private Long getCourseCount(Department department, Session session) {
        var holder = CriteriaHolder.getQueryHolder(session, Long.class, Course.class);
        holder.getQuery()
                .where(
                        holder.getBuilder()
                                .equal(holder.getRoot().get(Course_.department), department)
                );

        holder.getQuery()
                .select(
                        holder.getBuilder()
                                .count(holder.getRoot().get(Course_.id))
                );

        return session.createQuery(holder.getQuery())
                .setReadOnly(true)
                .setMaxResults(1)
                .getSingleResult();
    }
}

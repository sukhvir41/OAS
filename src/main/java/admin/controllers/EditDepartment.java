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
import java.util.stream.Collectors;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/edit-department")
public class EditDepartment extends Controller {

    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {

        var departmentIdString = req.getParameter("departmentId");

        if (StringUtils.isBlank(departmentIdString)) {
            resp.sendRedirect(
                    new UrlBuilder().addErrorParameter()
                            .addMessage("The department you are looking for does not exists")
                            .getUrl("/OAS/admin/departments")
            );
            return;
        }

        long departmentId = Long.parseLong(departmentIdString);

        var graph = session.createEntityGraph(Department.class);
        var subGraph = graph.addSubGraph(Department_.teachers);
        subGraph.addSubGraph(TeacherDepartmentLink_.TEACHER)
                .addAttributeNode(Teacher_.USER);

        Department department = EntityHelper.getInstance(departmentId, Department_.id, Department.class, session, true, graph);

        if (department.getTeachers().isEmpty()) {
            req.setAttribute("message", "Can not set Hod as none of the teacher have enrolled for this department");
        }

        List<Teacher> teachers = department.getTeachers()
                .stream()
                .map(TeacherDepartmentLink::getTeacher)
                .sorted((t1, t2) -> StringUtils.compare(t1.getFName() + t1.getLName(), t2.getFName() + t2.getLName()))
                .collect(Collectors.toList());

        req.setAttribute("department", department);
        req.setAttribute("departments", getTransferDepartments(department, session));
        req.setAttribute("teachers", teachers);
        req.getRequestDispatcher("/WEB-INF/admin/edit-department.jsp")
                .include(req, resp);
    }

    private List<Department> getTransferDepartments(Department departmentToExclude, Session session) {
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

}

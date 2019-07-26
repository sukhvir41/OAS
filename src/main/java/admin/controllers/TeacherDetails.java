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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/teachers/teacher-details")
public class TeacherDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        var urlParameters = new UrlParameters();

        var teacherIdString = req.getParameter("teacherId");

        if (StringUtils.isBlank(teacherIdString)) {
            resp.sendRedirect(
                    urlParameters.addErrorParameter()
                            .addMessage("The teacher you are trying to access does not exist")
                            .getUrl("/OAS/admin/teachers")
            );
            return;
        }

        var teacherId = UUID.fromString(teacherIdString);

        var teacherGraph = session.createEntityGraph(Teacher.class);
        teacherGraph.addAttributeNodes(Teacher_.USER, Teacher_.CLASS_ROOM);
        var teacherDepartmentSubGraph = teacherGraph.addSubgraph(Teacher_.DEPARTMENTS);
        teacherDepartmentSubGraph.addAttributeNodes(TeacherDepartmentLink_.DEPARTMENT);

        Teacher teacher = EntityHelper.getInstance(teacherId, Teacher_.id, Teacher.class, session, true, teacherGraph);

        List<Department> departments = teacher.getDepartments()
                .stream()
                .map(TeacherDepartmentLink::getDepartment)
                .collect(Collectors.toList());


        var teacher1SubGraph = session.createEntityGraph(Teacher.class);
        teacher1SubGraph.addAttributeNodes(Teacher_.HOD_OF);
        var teacher1TeachingSubGraph = teacher1SubGraph.addSubGraph(Teacher_.TEACHES);
        teacher1TeachingSubGraph.addAttributeNodes(Teaching_.CLASS_ROOM, Teaching_.SUBJECT);

        Teacher teacher1 = EntityHelper.getInstance(teacherId, Teacher_.id, Teacher.class, session, true, teacher1SubGraph);


        req.setAttribute("teacher", teacher);
        req.setAttribute("user", teacher.getUser());
        req.setAttribute("departments", departments);
        req.setAttribute("isClassTeacher", !Objects.isNull(teacher.getClassRoom()));
        req.setAttribute("classRoom", teacher.getClassRoom());
        req.setAttribute("hodOf", new ArrayList<>(teacher1.getHodOf()));
        req.setAttribute("isHod", teacher.isHod());
        req.setAttribute("teachings", teacher1.getTeaches());

        req.getRequestDispatcher("/WEB-INF/admin/teacher-details.jsp")
                .include(req, resp);

    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var teacherIdString = req.getParameter("teacherId");
        var urlParameter = new UrlParameters()
                .addErrorParameter()
                .addMessage("An error occurred while getting teacher details with id " + teacherIdString);

        if (StringUtils.isBlank(teacherIdString)) {
            urlParameter.addMessage("An error occurred while getting the teacher details");
        }

        resp.sendRedirect(urlParameter.getUrl("/OAS/admin/teachers"));

    }
}

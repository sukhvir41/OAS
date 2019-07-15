/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.hibernate.Session;
import utility.Controller;

import javax.persistence.criteria.JoinType;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/department-details")
public class DepartmentDetails extends Controller {

    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {

        long departmentID = Long.parseLong(req.getParameter("departmentId"));

        Department department = EntityHelper.getInstance(departmentID, Department_.id, Department.class, session, true, Department_.HOD, Department_.COURSES);

        //this will fire another query get the teacher which is necessary
        List<Teacher> teachers = getTeachersOfDepartment(session, department);

        List<Course> courses = department.getCourses();

        Teacher hod = department.getHod();

        req.setAttribute("canDelete", teachers.isEmpty() && courses.isEmpty() && Objects.isNull(hod));
        req.setAttribute("department", department);
        req.setAttribute("hod", hod);
        req.setAttribute("teachers", teachers);
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/WEB-INF/admin/department-details.jsp")
                .include(req, resp);

    }


    private List<Teacher> getTeachersOfDepartment(Session session, Department department) {

        var builder = session.getCriteriaBuilder();
        var query = builder.createQuery(Teacher.class);
        var root = query.from(Teacher.class);

        var join = root.join(Teacher_.departments, JoinType.INNER);

        query.where(builder.equal(join.get(TeacherDepartmentLink_.department), department));


        return session.createQuery(query)
                .getResultList();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.controllers;

import entities.ClassRoom;
import entities.Department;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author sukhvir
 */
@WebServlet("/teacher/hod")
public class HodHome extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String departmentString = req.getParameter("departmentId");

        long departmentId;
        if (departmentString != null) {
            departmentId = Integer.valueOf(departmentString);
        } else {
            departmentId = ((Department) req.getSession().getAttribute("department")).getId();
        }
        Department department = (Department) session.get(Department.class, departmentId);
        req.getSession().setAttribute("department", department);
        req.setAttribute("courses", department.getCourses());
        List<ClassRoom> classRooms = session.createCriteria(ClassRoom.class)
                .add(Restrictions.in("course", department.getCourses()))
                .list();

        req.setAttribute("classRooms", classRooms);
        req.setAttribute("teachers", department.getTeachers());

        req.getRequestDispatcher("/WEB-INF/hod/hodhome.jsp").include(req, resp);

    }

}

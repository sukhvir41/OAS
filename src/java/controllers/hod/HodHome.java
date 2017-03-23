/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.hod;

import entities.ClassRoom;
import entities.Department;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet("/teacher/hod")
public class HodHome extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        String departmentString = req.getParameter("departmentId");
        try {
            int departmentId;
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
            req.getRequestDispatcher("/WEB-INF/hod/hodhome.jsp").forward(req, resp);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        } finally {
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.ClassRoom;
import entities.Course;
import entities.Department;
import entities.Subject;
import utility.Utils;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin")
public class AdminHome extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);

    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Department> departments;
        List<Course> courses;
        List<ClassRoom> classRooms;
        List<Subject> subjects;
        Session session = Utils.openSession();
        session.beginTransaction();
        departments = (List<Department>) session.createQuery("from Department").list();
        courses = (List<Course>) session.createQuery("from Course").list();
        classRooms = (List<ClassRoom>) session.createQuery("from ClassRoom").list();
        subjects = (List<Subject>) session.createQuery("from Subject").list();
        req.setAttribute("departments", departments);
        req.setAttribute("courses", courses);
        req.setAttribute("classRooms", classRooms);
        req.setAttribute("subjects", subjects);
        session.getTransaction().commit();
        session.close();
        req.getRequestDispatcher("WEB-INF/admin/adminhome.jsp").forward(req, resp);

    }

}

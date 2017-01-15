/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.ClassRoom;
import entities.Course;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms")
public class AdminClassRoom extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ClassRoom> classRooms;
        List<Course> courses;
        Session session = Utils.openSession();
        session.beginTransaction();
        classRooms = (List<ClassRoom>) session.createCriteria(ClassRoom.class).list();
        courses = (List<Course>) session.createCriteria(Course.class).list();
        req.setAttribute("classrooms", classRooms);
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/WEB-INF/admin/adminclassroom.jsp").forward(req, resp);
        session.getTransaction().commit();
        session.close();
    }

}

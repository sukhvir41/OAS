/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.ClassRoom;
import entities.Course;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms")
public class AdminClassRoom extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        List<ClassRoom> classRooms = (List<ClassRoom>) session.createCriteria(ClassRoom.class)
                .list();
        List<Course> courses = (List<Course>) session.createCriteria(Course.class)
                .list();
        
        req.setAttribute("classrooms", classRooms);
        req.setAttribute("courses", courses);
        
        req.getRequestDispatcher("/WEB-INF/admin/adminclassroom.jsp")
                .include(req, resp);

    }

}

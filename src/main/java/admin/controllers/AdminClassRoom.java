/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.*;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms")
public class AdminClassRoom extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var classRoomGraph = session.createEntityGraph(ClassRoom.class);
        classRoomGraph.addAttributeNodes(ClassRoom_.COURSE);

        List<ClassRoom> classRooms = EntityHelper.getAll(session, ClassRoom.class, ClassRoom_.name, classRoomGraph, true);

        List<Course> courses = EntityHelper.getAll(session, Course.class, Course_.name, true);

        req.setAttribute("classrooms", classRooms);
        req.setAttribute("courses", courses);

        req.getRequestDispatcher("/WEB-INF/admin/admin-classroom.jsp")
                .include(req, resp);
    }

}

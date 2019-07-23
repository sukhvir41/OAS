/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.ClassRoom;
import org.hibernate.Session;
import utility.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms/classroom-details")
public class ClassRoomDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {


        // update this agent
        long classRoomId = Long.parseLong(req.getParameter("classroomId"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classRoomId);

        req.setAttribute("classroom", classRoom);

        req.getRequestDispatcher("/WEB-INF/admin/classroom-details.jsp")
                .include(req, resp);

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.ClassRoom;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms/detailclassroom")
public class DetailClassRoom extends Controller {

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        long classRoomId = Long.parseLong(req.getParameter("classroomId"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classRoomId);
        
        req.setAttribute("classroom", classRoom);
        
        req.getRequestDispatcher("/WEB-INF/admin/detailclassroom.jsp")
                .include(req, resp);

    }

}

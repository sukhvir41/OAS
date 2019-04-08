/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
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
@WebServlet(urlPatterns = "/admin/classrooms/editclassroom")
public class EditClassRoom extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int classRoomId = Integer.parseInt(req.getParameter("classroomId"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classRoomId);

        req.setAttribute("classroom", classRoom);
        req.getRequestDispatcher("/WEB-INF/admin/editclassroom.jsp").include(req, resp);
    }

}

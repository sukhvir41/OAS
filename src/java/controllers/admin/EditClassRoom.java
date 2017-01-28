/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.ClassRoom;
import java.io.IOException;
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
@WebServlet(urlPatterns = "/admin/classrooms/editclassroom")
public class EditClassRoom extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int classRoomId;
        try {
            classRoomId = Integer.parseInt(req.getParameter("classroomId"));
        } catch (Exception e) {
            resp.sendRedirect("/OAS/error");
            return;
        }
        Session session = Utils.openSession();
        session.beginTransaction();
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classRoomId);
        session.getTransaction().commit();
        session.close();
        req.setAttribute("classroom", classRoom);
        req.getRequestDispatcher("/WEB-INF/admin/editclassroom.jsp").forward(req, resp);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

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
@WebServlet(urlPatterns = "/admin/classrooms/deleteclassroom")
public class DeleteClassRoom extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int classRoomId = Integer.parseInt(req.getParameter("classroomId"));
            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classRoomId);
            session.delete(classRoom);
            session.getTransaction().commit();
            session.close();
            resp.sendRedirect("/OAS/admin/classrooms");
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            
            resp.sendRedirect("/OAS/error");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin/classrooms");
    }
    
}

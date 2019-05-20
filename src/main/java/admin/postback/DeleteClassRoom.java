/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.ClassRoom;
import org.hibernate.Session;
import utility.PostBackController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms/deleteclassroom")
public class DeleteClassRoom extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int classRoomId = Integer.parseInt(req.getParameter("classroomId"));
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classRoomId);
        session.delete(classRoom);

        resp.sendRedirect("/OAS/admin/classrooms");

    }

}

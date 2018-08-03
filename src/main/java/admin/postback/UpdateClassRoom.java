/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.ClassRoom;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.PostBackController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms/updateclassroom")
public class UpdateClassRoom extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int classroomId = Integer.parseInt(req.getParameter("classroomId"));
        String name = req.getParameter("classroomname");
        String division = req.getParameter("division");
        int semister = Integer.parseInt(req.getParameter("semister"));
        int minimumSubjects = Integer.parseInt(req.getParameter("minimumsubjects"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
        classRoom.setName(name);
        classRoom.setDivision(division);
        classRoom.setMinimumSubjects(minimumSubjects);
        classRoom.setSemister(semister);
        session.update(classRoom);

        if (req.getParameter("from").equals("")) {
            resp.sendRedirect("/OAS/admin/classrooms/detailclassroom?classroomId=" + classroomId);
        } else {
            resp.sendRedirect("/OAS/admin/classrooms#" + classroomId);
        }

    }

}

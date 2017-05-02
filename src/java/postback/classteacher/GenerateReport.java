/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.classteacher;

import entities.ClassRoom;
import entities.Teacher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/classteacher/generatereportpost")
public class GenerateReport extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {

            process(req, resp, session, req.getSession());
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        } finally {

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/error");
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        int classroomId = Integer.parseInt(req.getParameter("classroom"));
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);
        if (teacher.getClassRoom().getId() == classRoom.getId()) {
            postback.admin.GenerateReport report = new postback.admin.GenerateReport();
            report.doPost(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.ClassRoom;
import entities.Subject;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
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
@WebServlet(urlPatterns = "/admin/subjects/detailsubject")
public class DetailSubject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int subjectId;
        try {
            subjectId = Integer.parseInt(req.getParameter("subjectId"));
        } catch (Exception e) {
            resp.sendRedirect("/OAS/error");
            return;
        }
        try {
            Session session = Utils.openSession();
            session.beginTransaction();
            Subject subject = (Subject) session.get(Subject.class, subjectId);
            List<ClassRoom> classRooms = subject.getCourse().getClassRooms();
            List<ClassRoom> classroom = new ArrayList<>();
            classRooms.stream()
                    .filter(e -> !subject.getClassRooms().contains(e))
                    .forEach(classroom::add);
            req.setAttribute("classrooms", classroom);
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("/WEB-INF/admin/detailsubject.jsp").include(
                    req, resp);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

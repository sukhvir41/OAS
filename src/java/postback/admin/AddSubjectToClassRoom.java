/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.ClassRoom;
import entities.Subject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
@WebServlet(urlPatterns = "/admin/subjects/addsubjecttoclassrooms")
public class AddSubjectToClassRoom extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            int subjectId = Integer.parseInt(req.getParameter("subjectId"));
            List<String> classRooms = Arrays.asList(req.getParameterValues("classes"));

            Subject subject = (Subject) session.get(Subject.class, subjectId);
            classRooms.stream()
                    .map(Integer::parseInt)
                    .map(classroomId -> (ClassRoom) session.get(ClassRoom.class, classroomId))
                    .forEach(classRoom -> subject.addClassRoom(classRoom));
            session.getTransaction().commit();
            session.close();
            resp.sendRedirect("/OAS/admin/subjects/detailsubject?subjectId=" + subjectId);
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();

            resp.sendRedirect("/OAS/error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/error");
    }

}

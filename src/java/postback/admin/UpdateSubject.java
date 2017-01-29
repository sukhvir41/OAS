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
import java.util.HashSet;
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
@WebServlet(urlPatterns = "/admin/subjects/updatesubject")
public class UpdateSubject extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int subjectId = Integer.parseInt(req.getParameter("subjectId"));
            String name = req.getParameter("subjectname");
            boolean elective = Boolean.parseBoolean(req.getParameter("elective"));
            System.out.println(req.getParameterValues("classes"));
            List<String> classes = new ArrayList<>(Arrays.asList(req.getParameterValues("classes")));
            Session session = Utils.openSession();
            session.beginTransaction();
            Subject subject = (Subject) session.get(Subject.class, subjectId);
            subject.setName(name);
            subject.setElective(elective);
            HashSet<ClassRoom> classRooms = new HashSet<>();
            classes.stream()
                    .map(Integer::parseInt)
                    .map(e -> (ClassRoom) session.get(ClassRoom.class, e))
                    .forEach(e -> classRooms.add(e));
            checkAndRemove(classRooms, subject);
            session.getTransaction().commit();
            session.close();
            if (req.getParameter("from").equals("") || req.getParameter("from") == null) {
                resp.sendRedirect("/OAS/admin/subjects/detailsubject?subjectId=" + subjectId);
            } else {
                resp.sendRedirect("/OAS/admin/subjects#" + subjectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin/subjects");
    }

    private void checkAndRemove(HashSet hashSet, Subject subject) {

        for (int i = 0; i < subject.getClassRooms().size(); i++) {
            if (!hashSet.contains(subject.getClassRooms().get(i))) { 
                subject.getClassRooms().get(i).getSubjects().remove(subject);
                subject.getClassRooms().remove(i);
            }
        }
    }

}

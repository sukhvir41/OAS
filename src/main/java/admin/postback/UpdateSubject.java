/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.ClassRoom;
import entities.Subject;
import org.hibernate.Session;
import utility.PostBackController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/updatesubject")
public class UpdateSubject extends PostBackController {
    // have to write a better code for this

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int subjectId = Integer.parseInt(req.getParameter("subjectId"));
        String name = req.getParameter("subjectname");
        boolean elective = Boolean.parseBoolean(req.getParameter("elective"));

        List<String> classes = new ArrayList<>(Arrays.asList(req.getParameterValues("classes")));

        Subject subject = (Subject) session.get(Subject.class, subjectId);
        subject.setName(name);
        subject.setElective(elective);
        HashSet<ClassRoom> classRooms = new HashSet<>();//contains the class room that should have this subject
        classes.stream()
                .map(Integer::parseInt)
                .map(e -> (ClassRoom) session.get(ClassRoom.class, e))
                .forEach(e -> classRooms.add(e));
        checkAndRemove(classRooms, subject);

        if (req.getParameter("from").equals("") || req.getParameter("from") == null) {
            resp.sendRedirect("/OAS/admin/subjects/detailsubject?subjectId=" + subjectId);
        } else {
            resp.sendRedirect("/OAS/admin/subjects#" + subjectId);
        }

    }

    private void checkAndRemove(HashSet hashSet, Subject subject) {
        List<ClassRoom> classes = new ArrayList<>();
        subject.getClassRooms()
                .stream()
                .filter(classRoom -> !hashSet.contains(classRoom))
                .forEachOrdered(classRoom -> { // find a better way to do this
                    classRoom.getSubjects().remove(subject);
                    classes.add(classRoom);
                });

        subject.getClassRooms().removeAll(classes);

    }

}

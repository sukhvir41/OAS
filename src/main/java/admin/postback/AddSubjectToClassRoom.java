/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.ClassRoom;
import entities.Subject;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/subjects/addsubjecttoclassrooms")
public class AddSubjectToClassRoom extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        int subjectId = Integer.parseInt(req.getParameter("subjectId"));
        List<String> classRooms = Arrays.asList(req.getParameterValues("classes"));

        Subject subject = (Subject) session.get(Subject.class, subjectId);
        classRooms.stream()
                .map(Integer::parseInt)
                .map(classroomId -> (ClassRoom) session.get(ClassRoom.class, classroomId))
                .forEach(classRoom -> subject.addClassRoom(classRoom));

        resp.sendRedirect("/OAS/admin/subjects/detailsubject?subjectId=" + subjectId);

    }

}

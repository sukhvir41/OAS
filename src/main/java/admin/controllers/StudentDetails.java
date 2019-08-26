/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.EntityHelper;
import entities.Student;
import entities.Student_;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/students/student-details")
public class StudentDetails extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession,
                        PrintWriter out) throws Exception {

        var studentIdString = req.getParameter("studentId");

        if (StringUtils.isBlank(studentIdString)) {
            resp.sendRedirect(
                    new UrlBuilder()
                            .addErrorParameter()
                            .addMessage("The student you are trying tp access does not exist")
                            .getUrl("/OAS/admin/Students")
            );
            return;
        }

        var studentId = UUID.fromString(studentIdString);

        var studentGraph = session.createEntityGraph(Student.class);
        studentGraph.addAttributeNodes(Student_.USER, Student_.CLASS_ROOM, Student_.SUBJECTS);

        var student = EntityHelper.getInstance(studentId, Student_.id, Student.class, session, true, studentGraph);

        req.setAttribute("student", student);
        req.setAttribute("username", student.getUser().getUsername());

        req.getRequestDispatcher("/WEB-INF/admin/student-details.jsp")
                .include(req, resp);

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.ClassRoom;
import entities.ClassRoom_;
import entities.EntityHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.UrlParameters;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/classrooms/edit-classroom")
public class EditClassRoom extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {


        var classRoomString = req.getParameter("classroomId");

        if (StringUtils.isBlank(classRoomString)) {
            resp.sendRedirect(
                    new UrlParameters().addErrorParameter()
                            .addMessage("The class room you are trying to access does not exist")
                            .getUrl("/OAS/admin/classrooms")
            );
            return;
        }

        var classRoomId = Long.parseLong(classRoomString);

        ClassRoom classRoom = EntityHelper.getInstance(classRoomId, ClassRoom_.id, ClassRoom.class, session, true);

        req.setAttribute("classroom", classRoom);
        req.getRequestDispatcher("/WEB-INF/admin/edit-classroom.jsp")
                .include(req, resp);
    }

}

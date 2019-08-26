/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hod.postback;

import admin.postback.GenerateReport;
import entities.ClassRoom;
import entities.Department;
import org.hibernate.Session;
import utility.ReportPostBackController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;

/**
 * @author sukhvir
 */
@WebServlet("/teacher/hod/generatereportpost")
public class HodReportGeneration extends ReportPostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, OutputStream out) throws Exception {
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        int classrommId = Integer.parseInt(req.getParameter("classroom"));
        ClassRoom classRomm = (ClassRoom) session.get(ClassRoom.class, classrommId);

        if (department.getId() == classRomm.getCourse().getDepartment().getId()) {
            GenerateReport reportGen = new GenerateReport();
            reportGen.process(req, resp, session, httpSession, out);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }

}

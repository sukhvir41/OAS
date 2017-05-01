/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.hod;

import entities.ClassRoom;
import entities.Department;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import postback.admin.GenerateReport;
import utility.PostBackController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet("/teacher/hod/generatereportpost")
public class HodReportGeneration extends HttpServlet {

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
        Department department = (Department) httpSession.getAttribute("department");
        department = (Department) session.get(Department.class, department.getId());

        int classrommId = Integer.parseInt(req.getParameter("classroom"));
        ClassRoom classRomm = (ClassRoom) session.get(ClassRoom.class, classrommId);

        if (department.getId() == classRomm.getCourse().getDepartment().getId()) {
            GenerateReport reportGen = new GenerateReport();
            reportGen.doPost(req, resp);
        } else {
            resp.sendRedirect("/OAS/error");
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;

/**
 *
 * @author sukhvir
 */
public abstract class ReportPostBackController extends HttpServlet {

    @Override
    final protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    final protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        onError(req, resp);
    }

    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(403);
    }

    public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        OutputStream out = resp.getOutputStream();

        try {

            process(req, resp, session, req.getSession(), out);
            session.getTransaction().commit();
            Utils.closeSession();

        } catch (Exception e) {

            session.getTransaction().rollback();
            Utils.closeSession();
            //e.printStackTrace();
            resp.sendError(500);

        } finally {
            out.close();
        }
    }

    public abstract void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession session0, OutputStream out) throws Exception;

}

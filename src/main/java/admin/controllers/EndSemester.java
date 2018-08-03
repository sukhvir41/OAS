/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.BackupData;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
public class EndSemester extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        List backupNames = session.createCriteria(BackupData.class)
                .setProjection(Projections.distinct(Projections.property("backupName")))
                .list();

        req.setAttribute("names", backupNames);
    }

}

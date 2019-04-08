/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import AttendanceServices.MacHandlers;
import utility.PostBackController;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/networksettingspost")
public class SetNetworkSettings extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String macAddress = req.getParameter("macaddress");
        String ipaddress = req.getParameter("ipaddress");

        MacHandlers.setHandles(ipaddress, macAddress);
        resp.sendRedirect("/OAS/admin/networksettings");
        req.getServletContext().setAttribute("ready", true);

//       
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin/networksettings?error=" + true);
        req.getServletContext().setAttribute("ready", false);
    }

}

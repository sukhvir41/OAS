/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.Controller;
import utility.MacAddressUtil;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/networksettings")
public class AdminNetworkSettings extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        req.setAttribute("macaddress", MacAddressUtil.getSourceMacAddress());
        req.setAttribute("ipaddress", MacAddressUtil.getSourceIpAddress());
        req.getRequestDispatcher("/WEB-INF/admin/adminnetworksettings.jsp").include(req, resp);
    }

}

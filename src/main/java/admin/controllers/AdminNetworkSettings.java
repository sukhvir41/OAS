/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import AttendanceServices.MacHandlers;
import utility.Controller;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/networksettings")
public class AdminNetworkSettings extends Controller {

    
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        req.setAttribute("macaddress", MacHandlers.getMachineMac());  //Todo: mac handler fic the issue
        req.setAttribute("ipaddress", MacHandlers.getMachineIp()); // Todo: fix the mac hadler
        req.setAttribute("handlerReady", MacHandlers.isHandlerReady());
        req.getRequestDispatcher("/WEB-INF/admin/adminnetworksettings.jsp").include(req, resp);
    }

}

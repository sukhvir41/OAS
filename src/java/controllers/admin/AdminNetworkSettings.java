/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility.MacAddressUtil;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/networksettings")
public class AdminNetworkSettings extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("macaddress", MacAddressUtil.getSourceMacAddress());
        req.setAttribute("ipaddress", MacAddressUtil.getSourceIpAddress());
        req.getRequestDispatcher("/WEB-INF/admin/adminnetworksettings.jsp").forward(req, resp);
    }

}

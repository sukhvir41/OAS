/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

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
@WebServlet(urlPatterns = "/admin/networksettingspost")
public class SetNetworkSettings extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            String macAddress = req.getParameter("macaddress");
            String ipaddress = req.getParameter("ipaddress");
            
            if (MacAddressUtil.setAddresses(macAddress, ipaddress)) {
                resp.sendRedirect("/admin/networksettings?error=" + false);
            } else {
                resp.sendRedirect("/admin/networksettings?error=" + true);
            }
        } catch (Exception e) {
            resp.sendError(500);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(500);
    }
    
}

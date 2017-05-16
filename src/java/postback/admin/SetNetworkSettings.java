/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.MacAddressUtil;
import utility.NewMacaddress;
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
        System.out.println(macAddress + "  " + ipaddress);
//            if (MacAddressUtil.setAddresses(macAddress, ipaddress)) {
        if (NewMacaddress.setAddresses(macAddress, ipaddress)) {
            resp.sendRedirect("/OAS/admin/networksettings?error=" + false);
        } else {
            resp.sendRedirect("/OAS/admin/networksettings?error=" + true);
        }

    }

}

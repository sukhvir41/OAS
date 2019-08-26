/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import student.attendanceWsService.MacHandlers;
import utility.PostBackController;
import utility.UrlBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/set-network-settings")
public class SetNetworkSettings extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String macAddress = req.getParameter("macAddress");
        String ipAddress = req.getParameter("ipAddress");

        var url = new UrlBuilder();

        if (StringUtils.isNoneBlank(macAddress, ipAddress)) {

            if (MacHandlers.setHandles(ipAddress, macAddress)) {
                req.getServletContext().setAttribute("ready", true);
            } else {
                url.addErrorParameter()
                        .addMessage("could not set the network setting")
                        .addParameter("ipAddress", ipAddress)
                        .addParameter("macAddress", macAddress);
            }

        } else {
            url.addErrorParameter()
                    .addMessage("Please provide the ip and mac address");
        }


        resp.sendRedirect(url.getUrl("/OAS/admin/network-settings"));
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/admin/network-settings?error=" + true);
        req.getServletContext().setAttribute("ready", false);
    }

}

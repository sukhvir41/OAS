/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.admin;

import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import utility.AjaxController;
import utility.NewMacaddress;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/configurenetwork")
public class ConfigureNetwork extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        String mac = req.getParameter("macaddress");
        String ip = req.getParameter("ipaddress");
        //out.print(MacAddressUtil.setAddresses(mac, ip));
        out.print(NewMacaddress.setAddresses(mac, ip));

    }

}

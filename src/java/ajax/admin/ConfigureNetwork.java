/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.security.ssl.Krb5Helper;
import utility.MacAddressUtil;
import utility.NewMacaddress;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/ajax/configurenetwork")
public class ConfigureNetwork extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            String mac = req.getParameter("macaddress");
            String ip = req.getParameter("ipaddress");
            //out.print(MacAddressUtil.setAddresses(mac, ip));
            out.print(NewMacaddress.setAddresses(mac, ip));
            
            out.close();
        } catch (Exception e) {
            out.print("false");
        } finally {
            out.close();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("false");
        out.close();
    }
    
}

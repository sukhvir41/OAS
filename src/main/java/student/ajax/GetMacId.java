/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ajax;

import org.hibernate.Session;
import utility.AjaxController;
import utility.NewMacaddress;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/student/ajax/getmacid")
public class GetMacId extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        //MacAddressUtil mac = new MacAddressUtil();
        // String macid = mac.getMacAddress(req.getRemoteAddr());
        String macid = NewMacaddress.getMacAddress(req.getRemoteAddr());
        System.out.println(macid + " :  " + req.getRemoteAddr());
        out.print(macid);

    }

}

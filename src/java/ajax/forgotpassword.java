/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import entities.Login;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/ajax/forgotpassword")
public class forgotpassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        PrintWriter out = resp.getWriter();
        if (email != null && !email.equals("")) {
            String body = "This is an auto generated mail. Do not reply or send any mail to this address."
                    + "Click on the link to reset your password in 30 mins or it will get expired";
            Session session = Utils.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Login where email = :email");
            query.setString("email", email);
            List<Login> list = query.list();
            if (list.size() != 0) {
                Login login = (Login) list.get(0);
                String token = Utils.createToken();
                login.setToken(token);
                login.setDate(new Date());
                session.update(login);
                String url = "192.168.1.1/OAS/resetpassword?username=" + login.getUsername() + "&token=" + token;
                Utils.sendMail(email, "Password reset.  OAS system", body + url);
                out.print("true");
            } else {
                out.print("true");
            }
            session.getTransaction().commit();
            session.close();

        } else {
            out.print("false");
        }
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        out.close();
    }

}

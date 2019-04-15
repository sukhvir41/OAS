package controllers;

import org.hibernate.Session;
import student.attendanceWsService.MacAddressListner;
import student.attendanceWsService.MacHandlers;
import utility.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


@WebServlet(urlPatterns = "/test-ws")
public class TestWS extends Controller {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        httpSession.setAttribute("ip", req.getRemoteAddr());

        boolean status = MacHandlers.setHandles("192.168.0.36", "08:00:27:66:EB:57");
        if (status) {
            MacHandlers.setPacketListners(new MacAddressListner());
        }


        out.println(status + " mac handler set status ");


        req.getRequestDispatcher("/WEB-INF/test-ws.html")
                .include(req, resp);
    }
}

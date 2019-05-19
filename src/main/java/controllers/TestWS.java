package controllers;

import org.hibernate.Session;
import student.attendanceWsService.MacAddressListner;
import student.attendanceWsService.MacHandlers;
import utility.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


@WebServlet(urlPatterns = "/test-ws")
public class TestWS extends Controller {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        httpSession.setAttribute("ip", req.getRemoteAddr());
        boolean status = MacHandlers.isHandlerReady();
        try {
            MacHandlers.setPacketListeners(new MacAddressListner());
        } catch (Exception e) {
            out.println(Arrays.toString(e.getStackTrace()));
        }
        out.println(status + " mac handler set status ");

        req.getRequestDispatcher("/WEB-INF/test-ws.html")
                .include(req, resp);
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("error occured");
        req.getRequestDispatcher("/WEB-INF/test-ws.html")
                .include(req, resp);
    }

    @Override
    public boolean showErrorLog() {
        return true;
    }
}

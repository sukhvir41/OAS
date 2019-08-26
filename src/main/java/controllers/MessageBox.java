package controllers;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.Controller;
import utility.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Base64;

@WebServlet(urlPatterns = "/WEB-INF/message-box")
public class MessageBox extends Controller {
    @Override
    public void process(
            HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out)
            throws Exception {

        String message = req.getParameter("message");

        if (StringUtils.isNotBlank(message)) {
            req.setAttribute(
                    "message",
                    StringUtils.toEncodedString(
                            Base64.getDecoder().decode(message),
                            Charset.forName(Utils.UTF8)
                    )
            );
        }

        String error = req.getParameter("error");
        if (StringUtils.isNotBlank(error)) {
            req.setAttribute("error", error);
        }

        String success = req.getParameter("success");
        if (StringUtils.isNotBlank(success)) {
            req.setAttribute("success", success);
        }

        req.getRequestDispatcher("/WEB-INF/message-box.jsp")
                .include(req, resp);
    }

    @Override
    public boolean closePrintWriter() {
        return false;
    }
}

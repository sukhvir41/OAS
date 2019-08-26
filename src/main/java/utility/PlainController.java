package utility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class PlainController extends HttpServlet {


    @Override
    final protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(500);
    }

    public void onErrorWithException(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        resp.sendError(500);
    }


    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setContentType(resp);

        PrintWriter out = resp.getWriter();

        try {

            process(req, resp, req.getSession(), out);

        } catch (Exception e) {

            if (showErrorLog()) {
                e.printStackTrace();
            }

            if (callOnError()) {
                this.onError(req, resp);
            } else {
                this.onErrorWithException(req, resp, e);
            }

        } finally {

            if (closePrintWriter()) {
                out.close();
            }

        }
    }

    public boolean closePrintWriter() {
        return true;
    }

    public void setContentType(HttpServletResponse response) {
        response.setContentType("text/html");
    }

    public boolean showErrorLog() {
        return true;
    }

    /**
     * if onErrorWithException is overwritten by child class then call onErrorWithException else call onError
     *
     * @return call onError or not
     */
    private boolean callOnError() {
        try {
            return this.getClass()
                    .getMethod("onErrorWithException", HttpServletRequest.class, HttpServletResponse.class, Exception.class)
                    .getDeclaringClass()
                    .equals(Controller.class);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public abstract void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            HttpSession httpSession,
            PrintWriter out) throws Exception;


}



package utility;

import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Controller extends HttpServlet {


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
        Session session = Utils.openSession();
        session.beginTransaction();
        PrintWriter out = resp.getWriter();

        try {

            process(req, resp, session, req.getSession(), out);

            session.getTransaction().commit();

        } catch (Exception e) {

            session.getTransaction().rollback();

            if (showErrorLog()) {
                e.printStackTrace();
            }

            if (callOnError()) {
                this.onError(req, resp);
            } else {
                this.onErrorWithException(req, resp, e);
            }

        } finally {
            session.close();
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
                    .getMethod("onErrorWithException")
                    .getDeclaringClass()
                    .equals(this.getClass());
        } catch (Exception e) {
            return false;
        }
    }

    public abstract void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception;


}



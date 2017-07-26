package utility;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;

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
    
    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
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
            
            this.onError(req, resp);
            
        } finally {
            Utils.closeSession();
            out.close();
        }
    }
    
    public boolean showErrorLog() {
        return true;
    }
    
    public abstract void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception;
}


package utility;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AjaxController extends Controller {

    @Override
    final public void setContentType(HttpServletResponse response) {
        response.setContentType("application/json");
    }
    
    
    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("error");
        
    }

    @Override
    final protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.onError(req, resp);
    }

}

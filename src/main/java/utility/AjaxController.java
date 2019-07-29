
package utility;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public abstract class AjaxController extends Controller {

    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";
    public static final String DATA = "data";

    public static final JsonObject SUCCESS_STATUS = new JsonObject();
    public static final JsonObject ERROR_STATUS = new JsonObject();

    static {
        SUCCESS_STATUS.addProperty(STATUS, SUCCESS);
        ERROR_STATUS.addProperty(STATUS, ERROR);
    }

    @Override
    final public void setContentType(HttpServletResponse response) {
        response.setContentType("application/json");
    }


    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print("{\"status\":\"error\"}");

    }

    @Override
    final protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.onError(req, resp);
    }

}

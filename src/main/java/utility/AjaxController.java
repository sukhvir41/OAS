
package utility;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public abstract class AjaxController extends Controller {

    private static final int PAGE_SIZE = 10;

    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";
    public static final String DATA = "data";

    private static final JsonObject SUCCESS_JSON = new JsonObject();
    private static final JsonObject ERROR_JSON = new JsonObject();


    static {
        SUCCESS_JSON.addProperty(STATUS, SUCCESS);
        ERROR_JSON.addProperty(STATUS, ERROR);
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


    public int getPageSize() {
        return PAGE_SIZE;
    }

    public JsonObject getSuccessJson() {
        return SUCCESS_JSON.deepCopy();
    }

    public JsonObject getErrorJson() {
        return ERROR_JSON.deepCopy();
    }

}

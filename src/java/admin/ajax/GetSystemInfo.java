/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.UserType;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hyperic.sigar.Sigar;
import utility.AjaxController;

/**
 *
 * @author development
 */
@WebServlet(urlPatterns = "/admin/ajax/systeminfo")
public class GetSystemInfo extends AjaxController {

    private static final String MEMORY_USED = "memoryUsed";
    private static final String MEMORY_TOTAL = "memoryTotal";
    private static final String MEMORY_USED_PERC = "memoryUsedPerc";
    private static final String CPU_USED_PERC = "cpuUsedPerc";


    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        Gson gson = new Gson();
        Sigar sigar = new Sigar();
        JsonObject info = new JsonObject();
        info.addProperty(MEMORY_USED, sigar.getMem().getActualUsed());
        info.addProperty(MEMORY_TOTAL, sigar.getMem().getTotal());
        info.addProperty(MEMORY_USED_PERC, sigar.getMem().getUsedPercent());
        info.addProperty(CPU_USED_PERC, (1d - sigar.getCpuPerc().getIdle())*100);
        info.addProperty(UserType.Admin.toString(), UserType.Admin.getCount());
        info.addProperty(UserType.Student.toString(), UserType.Student.getCount());
        info.addProperty(UserType.Teacher.toString(), UserType.Teacher.getCount());
        sigar.close();
        System.out.println(gson.toJson(info));
        out.println(gson.toJson(info));

    }

    @Override
    public boolean showErrorLog() {
        return true;
    }

   
    

}

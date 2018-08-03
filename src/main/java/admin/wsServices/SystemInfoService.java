/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.wsServices;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entities.UserType;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.Session;
import org.hyperic.sigar.Sigar;

/**
 *
 * @author development
 */
public class SystemInfoService implements Runnable {

    private static final Gson gson = new Gson();
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    private static final String MEMORY_USED = "memoryUsed";
    private static final String MEMORY_TOTAL = "memoryTotal";
    private static final String MEMORY_USED_PERC = "memoryUsedPerc";
    private static final String CPU_USED_PERC = "cpuUsedPerc";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";
    private static final String ERROR = "error";
    private static final String TYPE = "type";

    private static JsonObject ERRORJSON = new JsonObject();
    
    static {
        ERRORJSON.addProperty(TYPE, ERROR);
    }
            
    public static void addSession(Session theSession) {
        sessions.add(theSession);
    }

    public static void removeSession(Session theSession) {
        sessions.remove(theSession);
    }

    public static synchronized void sendSystemReport() throws Exception {
        Sigar sigar = new Sigar();
        JsonObject info = new JsonObject();
        info.addProperty(MEMORY_USED, sigar.getMem().getActualUsed());
        info.addProperty(MEMORY_TOTAL, sigar.getMem().getTotal());
        info.addProperty(MEMORY_USED_PERC, sigar.getMem().getUsedPercent());
        info.addProperty(CPU_USED_PERC, (1d - sigar.getCpuPerc().getIdle()) * 100);
        info.addProperty(UserType.Admin.toString(), UserType.Admin.getCount());
        info.addProperty(UserType.Student.toString(), UserType.Student.getCount());
        info.addProperty(UserType.Teacher.toString(), UserType.Teacher.getCount());
        sigar.close();

        JsonObject message = new JsonObject();
        message.addProperty(TYPE, DATA);
        message.add(MESSAGE, info);

        sessions.parallelStream()
                .forEach(session -> sendMessage(session, message));
    }

    private static void sendMessage(Session theSession, JsonElement theJsonElement) {
        theSession.getAsyncRemote()
                .sendText(gson.toJson(theJsonElement));

    }

    @Override
    public void run() {
        try {
            sendSystemReport();
        } catch (Exception ex) {
            sessions.parallelStream()
                .forEach(session -> sendError(session) );
        }
    }
    
    public static void sendError(Session theSession){
        theSession.getAsyncRemote()
                .sendText(gson.toJson(ERRORJSON));
    }

   
}

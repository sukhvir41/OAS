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
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

import javax.websocket.Session;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @author development
 *
 * has been setup in contextListner to run every 15 seconds
 */
public class SystemInfoService implements Runnable {

    private static final Gson gson = new Gson();
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    private static final SystemInfo INFO = new SystemInfo();
    private static final HardwareAbstractionLayer HARDWARE = INFO.getHardware();

    private static final String MEMORY_AVAILABLE = "memoryAvailable";
    private static final String MEMORY_TOTAL = "memoryTotal";
    private static final String CPU_USED_PERCENTAGE = "cpuUsedPercentage";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";
    private static final String ERROR = "error";
    private static final String TYPE = "type";

    private static JsonObject ERROR_JSON = new JsonObject();
    
    static {
        ERROR_JSON.addProperty(TYPE, ERROR);
    }
            
    public static void addSession(Session theSession) {
        sessions.add(theSession);
    }

    public static void removeSession(Session theSession) {
        sessions.remove(theSession);
    }

    public static synchronized void sendSystemReport() throws Exception {
        JsonObject info = new JsonObject();

        double cpuUsage = Arrays.stream(HARDWARE.getProcessor().getProcessorCpuLoadBetweenTicks())
                .map( e -> e*100d)
                .average()
                .orElse(0);

        info.addProperty(CPU_USED_PERCENTAGE, cpuUsage);
        info.addProperty(MEMORY_AVAILABLE, HARDWARE.getMemory().getAvailable());
        info.addProperty(MEMORY_TOTAL, HARDWARE.getMemory().getTotal());

        info.addProperty(UserType.Admin.toString(), UserType.Admin.getCount());
        info.addProperty(UserType.Student.toString(), UserType.Student.getCount());
        info.addProperty(UserType.Teacher.toString(), UserType.Teacher.getCount());

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
                .forEach(SystemInfoService::sendError);
        }
    }
    
    public static void sendError(Session theSession){
        theSession.getAsyncRemote()
                .sendText(gson.toJson(ERROR_JSON));
    }

   
}

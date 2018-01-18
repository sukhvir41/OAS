/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.websocket.Session;

/**
 *
 * @author development
 */
public class WsMessages {

    private static Gson gson = new Gson();
    private static final String TYPE = "type";
    private static final String ERROR = "error";
    private static final String CONNECTION = "connection";
    private static final String ATTENDANCE = "attendance";
    private static final String SUCCESS = "success";
    private static final String FAILED = "failed";
    private static final String MESSAGE = "message";

    public static void sendErrorMessage(Session session, JsonObject theJson) {
        if (theJson.get(MESSAGE).getAsString() != null) {
            theJson.addProperty(TYPE, ERROR);

            sendMessage(session, theJson);
        }
    }

    public static void sendErrorMessage(Session session, String message) {
        JsonObject json = new JsonObject();
        json.addProperty(TYPE, ERROR);
        json.addProperty(MESSAGE, message);

        sendMessage(session, json);
    }

    public static void sendConnectionTerminated(Session session) {
        JsonObject json = new JsonObject();
        json.addProperty(TYPE, CONNECTION);
        json.addProperty(MESSAGE, FAILED);

        sendMessage(session, json);
    }

    public static void sendConnectionSucessfull(Session session) {
        JsonObject json = new JsonObject();
        json.addProperty(TYPE, CONNECTION);
        json.addProperty(MESSAGE, SUCCESS);

        sendMessage(session, json);
    }

    public static void sendAttendanceMessage(Session session,JsonObject theJson) {
        theJson.addProperty(TYPE, ATTENDANCE);
        
        sendMessage(session, theJson);
    }

    private static void sendMessage(Session session, JsonObject json) {
        session.getAsyncRemote()
                .sendText(gson.toJson(json));
    }
}

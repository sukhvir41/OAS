/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceWebScokets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *
 * @author development
 */
public enum StudentMessage {

    MESSAGE("message"), ERROR("error"), CONNECTION("connection");

    private static final String TYPE = "type";

    private Gson gson = new Gson();
    private String type;

    StudentMessage(String type) {
        this.type = type;
    }

    public String jsonToString(JsonObject json) {
        json.addProperty(TYPE, type); //this might create a problem
        return gson.toJson(json);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.JsonObject;
import entities.Lecture;
import entities.UserType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;

/**
 *
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {
            JsonObject obj = new JsonObject();
            obj.addProperty("h", "asdsd");
            System.out.println(obj.get("h").getAsString());
    }

}

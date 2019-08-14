/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import entities.*;
import org.hibernate.Session;

import javax.persistence.criteria.JoinType;
import java.io.IOException;
import java.io.Writer;
import java.security.URIParameter;
import java.util.stream.Collectors;


/**
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {

        Session session = Utils.openSession();
        session.beginTransaction();

        try {


            var query = session.createNativeQuery("" +
                    "select " +
                    "   cast(array_to_json(array_agg(row_to_json(d))) as varchar)" +
                    "from(" +
                    "   select" +
                    "       \"id\"," +
                    "       \"name\"" +
                    "   from" +
                    "   department" +
                    ") d");

            var departments = (String) query.getSingleResult();


            var jsonObject = new JsonObject();


            var array = new Gson().fromJson(departments, JsonArray.class);
            jsonObject.add("departments", array);
            System.out.println(new Gson().toJson(jsonObject));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        Utils.closeSessionFactory();
    }


}
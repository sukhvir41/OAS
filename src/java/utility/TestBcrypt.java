/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.*;
import entities.Course;
import entities.Login;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author sukhvir
 */
public class TestBcrypt {

    static JsonArray jsonCourses;

    public static void main(String[] args) {
        Session session = Utils.openSession();
        session.beginTransaction();
        Login login = (Login) session.get(Login.class, "s1");
        System.out.println(Math.abs(login.getDate().getTime() - new Date().getTime())/60000d);
        session.getTransaction().commit();
        session.close();
      
        

    }

    

}

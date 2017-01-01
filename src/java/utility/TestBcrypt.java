/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.*;
import entities.Course;
import entities.Login;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author sukhvir
 */
public class TestBcrypt {

    static JsonArray jsonCourses;

    public static void main(String[] args) throws Exception {
      //String pat = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher("sukhvir41@gmail.com").find();
        System.out.println(Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher("sukhvir41@gmail.com").find());
        System.out.println(Utils.regexMatch("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", "sukhvir41@gmail.com", Pattern.CASE_INSENSITIVE));
    }

}

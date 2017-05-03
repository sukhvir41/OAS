/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.*;
import java.util.Date;
import org.hibernate.Session;

/**
 *
 * @author Kalpesh
 */
public class AddToDatabase {

    public static void main(String[] args) {

        Session session = Utils.openSession();
        session.beginTransaction();
        
        session.save(Login.createAdminLogin("adminsukhvir", "qwertyuiop", "sukhvir41@gmail.com", AdminType.Main));
        session.save(Login.createAdminLogin("adminkalpesh", "qwertyuiop", "kalpeshrawal96@gmail.com", AdminType.Main));
        

        session.getTransaction().commit();
        session.close();

        session = Utils.openSession();
        session.beginTransaction();
        //have to test cascaade
        session.getTransaction().commit();
        session.close();

    }

}

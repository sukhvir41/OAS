/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import org.hibernate.Session;

import entities.Admin;
import entities.AdminType;
import entities.User;
import entities.UserType;

/**
 * @author Kalpesh
 */
public class AddToDatabase {

    public static void main(String[] args) {

        Session session = Utils.openSession();
        session.beginTransaction();

        try {
            Admin admin = new Admin("adminsukhvir12", "qwertyuiop", "sukhvir4112@gmail.com", AdminType.Main);
            session.save(admin);

            //session.save( new Admin( "adminkalpesh", "qwertyuiop", "kalpeshrawal96@gmail.com", AdminType.Main ) );
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        session.close();

    }
}

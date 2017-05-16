/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.*;
import org.hibernate.Session;

/**
 *
 * @author Kalpesh
 */
public class AddToDatabase {

    public static void main(String[] args) {

        Session session = Utils.openSession();
        session.beginTransaction();

        session.save(new Admin("adminsukhvir", "qwertyuiop", "sukhvir41@gmail.com", AdminType.Main));

        session.getTransaction().commit();
        Utils.closeSession();
        Utils.closeSesssioFactory();

    }

}

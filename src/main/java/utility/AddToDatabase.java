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

		Admin admin = new Admin( "adminsukhvir12", "qwertyuiop", "sukhvir4112@gmail.com", AdminType.Main );
		//User user = new User( "adminsukhvir", "qwertyuiop", "sukhvir41@gmail.com", -2, UserType.Admin );


		//session.save( user );
		//System.out.println( user.getId() );
		//admin.setUser( user );
		session.save( admin );

		//session.save( new Admin( "adminkalpesh", "qwertyuiop", "kalpeshrawal96@gmail.com", AdminType.Main ) );
		session.getTransaction().commit();
		session.close();
		Utils.closeSessionFactory();

	}

}

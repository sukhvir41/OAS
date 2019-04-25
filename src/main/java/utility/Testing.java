/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import org.hibernate.Session;

/**
 * @author sukhvir
 */
public class Testing {

	public static void main(String[] args) throws Exception {

		Session session = Utils.openSession();
		session.beginTransaction();

		session.getTransaction().commit();
		Utils.closeSessionFactory();
	}

}
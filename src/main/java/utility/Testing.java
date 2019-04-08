/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.UUID;

import org.hibernate.Session;

import entities.Admin;
import entities.AdminType;
import entities.Department;
import entities.User;

/**
 * @author sukhvir
 */
public class Testing {

	public static void main(String[] args) throws Exception {

		Session session = Utils.openSession();
		session.beginTransaction();

		//Admin admin = new Admin( "adminsukhvir12", "qwertyuiop", "sukhvir4112@gmail.com", AdminType.Main );

		//session.save( admin );

		//System.out.println( admin.getId() );

		//session.get( User.class , UUID.fromString( "50aacd02-568e-46e5-94ed-ed39194522df" ) );
		//session.get( Admin.class , UUID.fromString( "50aacd02-568e-46e5-94ed-ed39194522df" ) );

		//Student s = new Student( 12, "ds", "dsf", null, "test", "test", "test@test.com", 454566l );

		//session.save( s );
		//System.out.println( s.getId() );

		//session.get( Student.class, UUID.fromString( "6e5cf0ea-bc14-4417-be31-106594bd3822" ) );

		//Department department = new Department( "IT" );

		//Course course = new Course( "bscit", department );

		//session.save( department );
		//session.save( course );

		Department.getDepartment( 7l,session );

		session.getTransaction().commit();
		Utils.closeSessionFactory();

		/*UrlParameters parameters = new UrlParameters();
		parameters.addParamter( "name","sukhvir" );
		parameters.addMessage( "the is a test" );
		parameters.addParamter( "test","test" );

		System.out.println(parameters.getUrl( "OAS" ));*/


	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Student;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;

/**
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {

	/*	Session session = Utils.openSession();
		session.beginTransaction();



		session.getTransaction().commit();
		Utils.closeSessionFactory();*/
/*
        Stream.of(Department.class.getDeclaredFields())
                .filter(e -> containsAnnotaions(e.getDeclaredAnnotations()))
                .forEach(e -> System.out.println(e.getName()));*/


    }

    public static boolean containsAnnotaions(Annotation[] annotations) {

        var oneToOneName = OneToOne.class.getName();
        var oneToManyName = OneToMany.class.getName();
        var manyToManyName = ManyToMany.class.getName();
        var manyToOneName = ManyToOne.class.getName();


        return Stream.of(annotations)
                //.peek( a -> System.out.println(a.annotationType().getName() + " - " + oneToManyName+ " - " + oneToOneName + " - " + manyToManyName))
                .anyMatch(a -> StringUtils.equalsAnyIgnoreCase(a.annotationType().getName(), oneToManyName, oneToOneName, manyToManyName, manyToOneName));


    }

}
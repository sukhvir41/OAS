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
        Department depart = new Department("IT");
        Course course = new Course("BscIt", depart);
        Subject subject = new Subject("php", false, course);
        ClassRoom classroom = new ClassRoom("fy bsc it", "A", 1, course);
        classroom.getSubjects().add(subject);
        Student student = new Student(1, "sukhvir", "thapar", 9769042141l, "sukhvir41@gmail.com", classroom, Boolean.TRUE);
        student.addSubject(subject);
        Teacher teacher = new Teacher("Sunita", "Jena", 0, "abc@xyz.com", true, false, classroom);
        teacher.addDepartment(depart);

        session.save(depart);
        session.save(course);
        session.save(subject);
        session.save(classroom);
        session.save(student);
        session.save(teacher);

        Teaching t = new Teaching(teacher, classroom, subject);
        teacher.addTeaching(t);
        Lecture lec = new Lecture(1, t);
        lec.setDate(new Date());
        lec.setId("lec1");
        session.save(t);
        session.save(lec);

        Login l = new Login("s1", "123456", "student", 1, "email", "123456", new Date());
        Login l1 = new Login("t1", "123456", "teacher", 1, "email", "123456", new Date());

        System.out.println("called");
        session.save(l);
        session.save(l1);
        System.out.println(student.getId());
        session.getTransaction().commit();
        session.close();

    }

}

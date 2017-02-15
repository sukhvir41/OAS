/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.ClassRoom;
import entities.Student;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {
        Session session = Utils.openSession();
        session.beginTransaction();  
                
        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, 1);
        List<Student> students = session.createCriteria(Student.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .list();
        
        System.out.println(students.size());
        
        session.getTransaction().commit();
        session.close();

    }

}

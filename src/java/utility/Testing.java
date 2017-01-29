/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.ClassRoom;
import entities.Subject;
import org.hibernate.Session;

/**
 *
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {
        ClassRoom c;
        Subject s;
        Session session = Utils.openSession();
        session.beginTransaction();
        s = (Subject) session.get(Subject.class, 1);
        ClassRoom c1 = s.getClassRooms().get(0);
        c = (ClassRoom) session.get(ClassRoom.class, c1.getId());
        System.out.println(s.getClassRooms().contains(c));
        session.getTransaction().commit();
        session.close();

    }

}

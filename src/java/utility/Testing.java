/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Teacher;
import org.hibernate.Session;

/**
 *
 * @author sukhvir
 */
public class Testing {
   
    public static void main(String[] args) throws Exception {
        Session session = Utils.openSession();
        session.beginTransaction();  
                Teacher t = (Teacher) session.get(Teacher.class,1);
                System.out.println(t);
                t.getTeaches().stream().forEach(e ->System.out.println(e.getSubject()));
        session.getTransaction().commit();
        session.close();
        
    }

    
    
    
    

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.Lecture;
import java.util.Calendar;
import java.util.Date;
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
        try {
            Date now = new Date();
            Calendar past = Calendar.getInstance();
            past.add(Calendar.DAY_OF_YEAR, -7);
            List<Lecture> lectures = session.createCriteria(Lecture.class)
                    .add(Restrictions.between("date", past.getTime(), now))
                    .list();
            System.out.println(lectures.size());
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();

        } finally {
        }

    }

}

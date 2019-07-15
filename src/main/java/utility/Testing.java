/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entities.*;
import org.hibernate.Session;

import javax.persistence.criteria.JoinType;
import java.security.URIParameter;
import java.util.stream.Collectors;


/**
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {

       /* Session session = Utils.openSession();
        session.beginTransaction();

        try {

            Department department = session.get(Department.class, 1l);

            var builder = session.getCriteriaBuilder();
            var query = builder.createQuery(Teacher.class);
            var root = query.from(Teacher.class);

            var join = root.join(Teacher_.departments, JoinType.INNER);

            query.where(builder.equal(join.get(TeacherDepartmentLink_.department), department));

            session.createQuery(query)
                    .getResultList()
                    .forEach(o -> System.out.println(o.getFName()));

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        Utils.closeSessionFactory();*/
    }


}
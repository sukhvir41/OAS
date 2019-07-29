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

        Session session = Utils.openSession();
        session.beginTransaction();

        try {

            var holder = CriteriaHolder.getQueryHolder(session, Department.class);

            holder.getQuery().orderBy(holder.getBuilder().asc(holder.getRoot().get(Department_.name)));

            holder.getQuery().where(
                holder.getBuilder().greaterThan(holder.getRoot().get(Department_.name),"Jooqtest2")
            );

            var results = session.createQuery(holder.getQuery())
                    .setMaxResults(6)
                    .getResultList();

            results.forEach(department -> System.out.println(department.getName() + "  " + department.getId()));




            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        Utils.closeSessionFactory();
    }


}
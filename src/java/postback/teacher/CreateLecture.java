/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.teacher;

import entities.Lecture;
import entities.Teaching;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/createlecture")
public class CreateLecture extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int count;
        int tcsId;
        Session session = Utils.openSession();
        session.beginTransaction();
        String lectureId = Utils.getLectureId();
        try {
            Date now = new Date();
            Calendar future = Calendar.getInstance();
            count = Integer.parseInt(req.getParameter("count"));
            tcsId = Integer.parseInt(req.getParameter("tcsId"));
            Teaching teaching = (Teaching) session.get(Teaching.class, tcsId);
            List<Lecture> lectures = session.createCriteria(Lecture.class)
                    .add(Restrictions.eq("teaching", teaching))
                    .addOrder(Order.desc("date"))
                    .list();
            if (lectures.size() > 0) {
                Lecture lecture = lectures.get(0);
                future.setTime(lecture.getDate());
                future.add(Calendar.MINUTE, 50 * lecture.getCount());
                if (lecture.getTeaching().equals(teaching) && now.after(lecture.getDate()) && now.before(future.getTime())) {
                    resp.sendRedirect("/OAS/teacher");
                } else {
                    Lecture lec = new Lecture(count, teaching);
                    lec.setId(lectureId);
                    lec.setDate(now);
                    session.save(lec);
                    resp.sendRedirect("/OAS/teacher");
                }
            } else {
                Lecture lec = new Lecture(count, teaching);
                lec.setId(lectureId);
                lec.setDate(now);
                session.save(lec);
                resp.sendRedirect("/OAS/teacher");
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/error");
    }

}

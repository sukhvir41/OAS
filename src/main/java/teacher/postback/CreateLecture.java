/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.postback;

import entities.Lecture;
import entities.Teaching;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import utility.PostBackController;
import utility.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/createlecture")
public class CreateLecture extends PostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        int count;
        int tcsId;

        String lectureId = Utils.getLectureId(session);

        LocalDateTime presentTime = LocalDateTime.now();
        count = Integer.parseInt(req.getParameter("count"));
        tcsId = Integer.parseInt(req.getParameter("teaching"));
        Teaching teaching = (Teaching) session.get(Teaching.class, tcsId);
        List<Lecture> lectures = session.createCriteria(Lecture.class)
                .add(Restrictions.eq("teaching", teaching))
                .addOrder(Order.desc("date"))
                .list();
        if (lectures.size() > 0) {
            Lecture lecture = lectures.get(0);
            if (presentTime.isBefore(lecture.getDate().plusMinutes(50 * lecture.getCount()))) {
                resp.sendRedirect("/OAS/teacher?error=true");
            } else {
                Lecture lec = new Lecture(count, teaching);
                lec.setId(lectureId);
                lec.setDate(presentTime);
                session.save(lec);
                resp.sendRedirect("/OAS/teacher");
            }
        } else {
            Lecture lec = new Lecture(count, teaching);
            lec.setId(lectureId);
            lec.setDate(presentTime);
            session.save(lec);
            resp.sendRedirect("/OAS/teacher");
        }

    }

}

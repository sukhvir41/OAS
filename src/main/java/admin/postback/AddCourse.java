package admin.postback;

import entities.Course;
import entities.Department;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlBuilder;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/courses/add-course")
public class AddCourse extends PostBackController {

    @Override
    public void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            Session session,
            HttpSession httpSession,
            PrintWriter out) throws Exception {

        String departmentId = req.getParameter("departmentId");
        String courseName = req.getParameter("courseName");

        var urlBuilder = new UrlBuilder();

        if (StringUtils.isAnyBlank(departmentId, courseName)) {
            onErrorWithException(req, resp, BLANK_EXCEPTION);
            return;
        }

        long id = Long.parseLong(departmentId);

        Department department = session.get(Department.class, id);
        Course course = new Course(courseName, department);
        session.save(course);

        String from = req.getParameter("from");


        if (StringUtils.isNotBlank(from) && from.equalsIgnoreCase("department-details")) {
            urlBuilder.addSuccessParameter()
                    .addMessage(courseName + " was added to the department")
                    .addParameter("departmentId", departmentId)
                    .setUrl("/OAS/admin/departments/department-details");


        } else {
            urlBuilder.addSuccessParameter()
                    .addMessage(courseName + " was added")
                    .setUrl("/OAS/admin/courses");
        }
        redirect(urlBuilder.toString(), req);
    }

    @Override
    public void onErrorWithException(HttpServletRequest req, HttpServletResponse resp, Exception exception) throws ServletException, IOException {
        System.out.println(exception instanceof PersistenceException);
        UrlBuilder parameters = new UrlBuilder()
                .addErrorParameter()
                .addMessage("Unable to add course as the provided data was incorrect");

        String from = req.getParameter("from");
        String departmentId = req.getParameter("departmentId");

        if (StringUtils.isNoneBlank(from, departmentId)) {
            parameters.addParameter("departmentId", departmentId)
                    .setUrl("/OAS/admin/departments/department-details");
        } else {
            parameters.setUrl("/OAS/admin/courses");
        }

        redirect(parameters.getUrl(), req);
    }


}

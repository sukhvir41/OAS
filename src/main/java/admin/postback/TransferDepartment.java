package admin.postback;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlParameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/admin/departments/transfer-department")
public class TransferDepartment extends PostBackController {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {
        var fromString = req.getParameter("from");
        var toString = req.getParameter("to");

        if (StringUtils.isAnyBlank(fromString, toString)) {

            return;
        }

        out.println("Transferring...");

        var fromDepartmentId = Long.parseLong(fromString);
        var toDepartmentId = Long.parseLong(toString);

        var fromGraph = session.createEntityGraph(Department.class);
        var teacherDepartmentLinkSubGraph = fromGraph.addSubgraph(Department_.teachers);
        //teacherDepartmentLinkSubGraph.addAttributeNode(TeacherDepartmentLink_.TEACHER);
        teacherDepartmentLinkSubGraph.addSubGraph(TeacherDepartmentLink_.TEACHER)
                .addAttributeNodes(Teacher_.USER);// will not need this if one to one lazy loading works on BE
        var fromDepartment = EntityHelper.getInstance(fromDepartmentId, Department_.id, Department.class, session, true, fromGraph);
        var toDepartment = EntityHelper.getInstance(toDepartmentId, Department_.id, Department.class, session, true, fromGraph);

        transferCourses(fromDepartment, toDepartment, session);

        transferTeachers(fromDepartment, toDepartment, session);

        resp.sendRedirect(
                new UrlParameters().addSuccessParameter()
                        .addMessage("The transfer was successful from " + fromDepartment.getName() + " to " + toDepartment.getName())
                        .addParameter("departmentId", toDepartmentId)
                        .getUrl("/OAS/admin/departments/department-details")

        );
    }

    @Override
    public void onError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var url = new UrlParameters()
                .addErrorParameter();

        var to = req.getParameter("to");
        var from = req.getParameter("from");

        if (StringUtils.isAnyBlank(to, from)) {
            if (StringUtils.isNotBlank(from)) {
                url.addMessage("Please select the department to transfer the courses and the teachers")
                        .addParameter("departmentId", from);
                resp.sendRedirect(url.getUrl("/OAS/admin/departments/edit-department"));
            } else {
                url.addMessage("Please provide the correct details for department transfer");
                resp.sendRedirect(url.getUrl("/OAS/admin/departments"));
            }

        } else {
            url.addMessage("An error occurred while transferring courses and teachers")
                    .addParameter("departmentId", from);
            resp.sendRedirect(url.getUrl("/OAS/admin/departments/edit-department"));
        }
    }

    private void transferCourses(Department fromDepartment, Department toDepartment, Session session) {
        var holder = CriteriaHolder.getUpdateHolder(session, Course.class);

        holder.getQuery()
                .set(Course_.department, toDepartment);

        holder.getQuery()
                .where(
                        holder.getBuilder()
                                .equal(holder.getRoot().get(Course_.department), fromDepartment)
                );

        session.createQuery(holder.getQuery())
                .executeUpdate();
    }

    private void transferTeachers(Department fromDepartment, Department toDepartment, Session session) {
        //insert teachers into the toDepartment
        var teachersFromToDepartment = toDepartment.getTeachers()
                .stream()
                .map(TeacherDepartmentLink::getTeacher)
                .collect(Collectors.toSet());

        fromDepartment.getTeachers()
                .stream()
                .map(TeacherDepartmentLink::getTeacher)
                .filter(teacher -> !teachersFromToDepartment.contains(teacher))// remove teachers that already in the toDepartment
                .forEach(teacher -> session.save(new TeacherDepartmentLink(teacher, toDepartment))); // have to enable batching for this

        // delete all teacher departmentLink where department  = fromDepartment

        var holder = CriteriaHolder.getDeleteHolder(session, TeacherDepartmentLink.class);

        holder.getQuery()
                .where(
                        holder.getBuilder()
                                .equal(holder.getRoot().get(TeacherDepartmentLink_.department), fromDepartment)
                );

        session.createQuery(holder.getQuery())
                .executeUpdate();
    }

}

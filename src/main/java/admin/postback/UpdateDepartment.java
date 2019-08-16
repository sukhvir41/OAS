/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.CriteriaHolder;
import entities.Department;
import entities.Department_;
import jooq.entities.Tables;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlParameters;
import utility.Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments/update-department")
public class UpdateDepartment extends PostBackController {

    @Override
    public void process(HttpServletRequest req,
                        HttpServletResponse resp,
                        Session session,
                        HttpSession httpSession,
                        PrintWriter out) throws Exception {

        String theDepartmentId = req.getParameter("departmentId");
        String theTeacherId = req.getParameter("teacherId");
        String theDepartmentName = req.getParameter("departmentName");

        UrlParameters parameters = new UrlParameters();

        if (StringUtils.isAnyBlank(theDepartmentId, theDepartmentName)) {
            parameters.addErrorParameter()
                    .addMessage("Unable to edit the department as details were missing");
            if (StringUtils.isBlank(theDepartmentId)) {
                resp.sendRedirect(parameters.getUrl("/OAS/admin/departments"));
            } else {
                resp.sendRedirect(parameters.addParameter("departmentId", theDepartmentId)
                        .getUrl("/OAS/admin/department-details"));
            }
            return;
        }

        var departmentId = Long.parseLong(theDepartmentId);

        // updating the department by setting the name
        var sql = Utils.getDsl()
                .update(Tables.DEPARTMENT)
                .set(Tables.DEPARTMENT.NAME, theDepartmentName);

        if (StringUtils.isNotBlank(theTeacherId)) {
            // updating the department hod
            sql = sql.set(Tables.DEPARTMENT.HOD_TEACHER_FID, UUID.fromString(theTeacherId));
        }
        //setting the condition which department to update
        var updateQuery = sql.where(Tables.DEPARTMENT.ID.eq(departmentId));

        Utils.executeNativeQuery(session, updateQuery);

        resp.sendRedirect(
                parameters.addSuccessParameter()
                        .addMessage(theDepartmentName + " was updated")
                        .addParameter("departmentId", theDepartmentId)
                        .getUrl("/OAS/admin/departments/department-details")
        );
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.PostBackController;
import utility.UrlParameters;

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
                resp.sendRedirect(parameters.addParamter("departmentId", theDepartmentId)
                        .getUrl("/OAS/admin/department-details"));
            }
            return;
        }

        var departmentId = Long.parseLong(theDepartmentId);

        var departmentRootGraph = session.createEntityGraph(Department.class);
        var hodSubGraph = departmentRootGraph.addSubgraph(Department_.hod); //get hod
        hodSubGraph.addAttributeNode(Teacher_.hodOf);

        Department department = EntityHelper.getInstance(departmentId, Department_.id, Department.class, session, false, departmentRootGraph);
        department.setName(theDepartmentName);

        if (StringUtils.isNotBlank(theTeacherId)) {
            var teacherId = UUID.fromString(theTeacherId);

            var teacherRootGraph = session.createEntityGraph(Teacher.class);
            var hodOfSubGraph = teacherRootGraph.addSubGraph(Teacher_.hodOf);
            hodOfSubGraph.addAttributeNode(Department_.HOD);

            var teacher = EntityHelper.getInstance(teacherId, Teacher_.id, Teacher.class, session, false, teacherRootGraph);
            teacher.addHodOf(department);
        }


        resp.sendRedirect(
                parameters.addSuccessParameter()
                        .addMessage(theDepartmentName + " was updated")
                        .addParamter("departmentId", theDepartmentId)
                        .getUrl("/OAS/admin/departments/department-details")
        );
    }


}

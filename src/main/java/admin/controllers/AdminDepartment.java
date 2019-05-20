/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controllers;

import entities.Department;
import entities.Department_;
import org.hibernate.Session;
import utility.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/departments")
public class AdminDepartment extends Controller {

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession,
			PrintWriter out) throws Exception {

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Department> query = builder.createQuery(Department.class);
		Root<Department> departmentRoot = query.from(Department.class);
		query.orderBy(builder.asc(departmentRoot.get(Department_.NAME)));

		List<Department> departments = session.createQuery(query).setReadOnly(true).list();

		req.setAttribute("departments", departments);

		req.getRequestDispatcher("/WEB-INF/admin/admin-department.jsp").include(req, resp);
	}

}

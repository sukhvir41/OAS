package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Department;
import entities.Department_;
import org.hibernate.Session;
import utility.AjaxController;
import utility.CriteriaHolder;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = "/admin/ajax/get-departments")
public class GetDepartments extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var pageValue = req.getParameter("pageValue");
        var searchText = req.getParameter("searchText");

        var holder = CriteriaHolder.getQueryHolder(session, Department.class);

        List<Predicate> predicates = new ArrayList<>();

        addPageValueCondition(pageValue, holder, predicates);
        addSearchTextCondition(searchText, holder, predicates);

        var departments = getDepartments(session, holder, predicates);

        var output = super.getSuccessJson();

        addDataToJson(output, departments);

        out.println(new Gson().toJson(output));
    }


    private void addPageValueCondition(String pageValue, CriteriaHolder<CriteriaQuery<Department>, Department> holder, List<Predicate> predicates) {

        if (pageValue == null || pageValue.isBlank()) {
            return;
        }

        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(Department_.name), pageValue)
        );
    }

    private void addSearchTextCondition(String searchText, CriteriaHolder<CriteriaQuery<Department>, Department> holder, List<Predicate> predicates) {

        if (searchText == null || searchText.isBlank()) {
            return;
        }

        predicates.add(
                holder.getBuilder()
                        .like(holder.getBuilder().lower(holder.getRoot().get(Department_.name)), searchText.toLowerCase() + "%")
        );
    }

    private List<Department> getDepartments(Session session, CriteriaHolder<CriteriaQuery<Department>, Department> holder, List<Predicate> predicates) {
        holder.getQuery()
                .where(holder.getBuilder().and(predicates.toArray(new Predicate[0])))
                .orderBy(holder.getBuilder().asc(holder.getRoot().get(Department_.name)));

        return session.createQuery(holder.getQuery())
                .setMaxResults(super.getPageSize() + 1)
                .setReadOnly(true)
                .getResultList();
    }

    private void addDataToJson(JsonObject successJson, List<Department> departments) {

        setMorePageProperty(successJson, departments);
        removeExtraDepartment(departments);
        setPageValueProperty(successJson, departments);

        JsonArray data = departments.stream()
                .map(this::mapDepartmentToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        successJson.add(DATA, data);
    }

    private void setMorePageProperty(JsonObject successJson, List<Department> departments) {
        successJson.addProperty("more", departments.size() == super.getPageSize() + 1);
    }

    private void removeExtraDepartment(List<Department> departments) {
        if (departments.size() == super.getPageSize() + 1) {
            departments.remove(departments.size() - 1);
        }
    }

    private void setPageValueProperty(JsonObject successJson, List<Department> departments) {
        if (departments.size() > 0) {
            successJson.addProperty("pageValue", departments.get(departments.size() - 1).getName());
        } else {
            successJson.addProperty("pageValue", "");
        }
    }

    private JsonObject mapDepartmentToJson(Department department) {
        var json = new JsonObject();

        json.addProperty("id", department.getId());
        json.addProperty("name", department.getName());

        return json;
    }


}

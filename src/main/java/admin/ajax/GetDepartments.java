package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.CriteriaHolder;
import entities.Department;
import entities.Department_;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.AjaxController;

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

        if (StringUtils.isNotBlank(pageValue)) {
            predicates.add(
                    holder.getBuilder()
                            .greaterThan(holder.getRoot().get(Department_.name), pageValue)
            );
        }

        if (StringUtils.isNotBlank(searchText)) {
            predicates.add(
                    holder.getBuilder()
                            .like(holder.getBuilder().lower(holder.getRoot().get(Department_.name)), searchText.toLowerCase() + "%")
            );
        }

        var predicatesArray = new Predicate[predicates.size()];
        predicatesArray = predicates.toArray(predicatesArray);

        holder.getQuery()
                .where(holder.getBuilder().and(predicatesArray));

        holder.getQuery()
                .orderBy(holder.getBuilder().asc(holder.getRoot().get(Department_.name)));

        var results = session.createQuery(holder.getQuery())
                .setMaxResults(super.getPageSize() + 1)
                .getResultList();

        var output = super.getSuccessJson().deepCopy();

        var columns = new JsonArray();
        columns.add("Name");
        output.add("columns", columns);

        if (results.size() == super.getPageSize() + 1) {
            output.addProperty("more", true);
            results.remove(results.size() - 1);
        } else {
            output.addProperty("more", false);
        }

        JsonArray data = results.stream()
                .map(this::mapDepartmentToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        output.add(DATA, data);
        if (data.size() > 0) {
            output.addProperty("pageValue", results.get(results.size() - 1).getName());
        } else {
            output.addProperty("pageValue", "");
        }

        out.println(new Gson().toJson(output));
    }

    private JsonObject mapDepartmentToJson(Department department) {
        var json = new JsonObject();

        json.addProperty("id", department.getId());
        json.addProperty("name", department.getName());

        return json;
    }


}

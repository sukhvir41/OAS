package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Course;
import entities.Course_;
import entities.CriteriaHolder;
import entities.Department_;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.AjaxController;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = "/admin/ajax/get-courses")
public class GetCourses extends AjaxController {

    private static final int PAGE_SIZE = 10;

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var pageValue = req.getParameter("pageValue");
        var searchText = req.getParameter("searchText");

        var holder = CriteriaHolder.getQueryHolder(session, Course.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(pageValue)) {
            predicates.add(
                    holder.getBuilder()
                            .greaterThan(holder.getRoot().get(Course_.name), pageValue)
            );
        }

        if (StringUtils.isNotBlank(searchText)) {
            var departmentJoinQuery = holder.getRoot()
                    .join(Course_.department, JoinType.INNER);

            predicates.add(
                    holder.getBuilder().or(
                            holder.getBuilder()
                                    .like(holder.getBuilder().lower(holder.getRoot().get(Course_.name)), searchText.toLowerCase() + "%"),
                            holder.getBuilder()
                                    .like(holder.getBuilder().lower(departmentJoinQuery.get(Department_.name)), searchText.toLowerCase() + "%")
                    )
            );
        }

        var predicatesArray = new Predicate[predicates.size()];
        predicatesArray = predicates.toArray(predicatesArray);

        var graph = session.createEntityGraph(Course.class);
        graph.addAttributeNodes(Course_.DEPARTMENT);

        holder.getQuery()
                .where(holder.getBuilder().and(predicatesArray))
                .orderBy(holder.getBuilder().asc(holder.getRoot().get(Course_.name)));

        var results = session.createQuery(holder.getQuery())
                .applyLoadGraph(graph)
                .setMaxResults(PAGE_SIZE + 1)
                .getResultList();

        var output = SUCCESS_STATUS.deepCopy();

        var columns = new JsonArray();
        columns.add("Name");
        columns.add("Department");
        output.add("columns", columns);

        if (results.size() == PAGE_SIZE + 1) {
            output.addProperty("more", true);
            results.remove(results.size() - 1);
        } else {
            output.addProperty("more", false);
        }

        JsonArray data = results.stream()
                .map(this::mapCourseToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        output.add(DATA, data);
        if (data.size() > 0) {
            output.addProperty("pageValue", results.get(results.size() - 1).getName());
        } else {
            output.addProperty("pageValue", "");
        }

        out.println(
                new Gson().toJson(output)
        );
    }

    private JsonObject mapCourseToJson(Course course) {
        var json = new JsonObject();

        json.addProperty("id", course.getId());
        json.addProperty("name", course.getName());
        json.addProperty("departmentId", course.getDepartment().getId());
        json.addProperty("departmentName", course.getDepartment().getName());

        return json;
    }

}

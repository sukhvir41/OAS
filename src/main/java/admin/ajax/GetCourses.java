package admin.ajax;

import com.google.gson.*;
import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import utility.AjaxController;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@WebServlet(urlPatterns = "/admin/ajax/get-courses")
public class GetCourses extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        Gson gson = new Gson();

        var pageValue = req.getParameter("pageValue");
        var searchText = req.getParameter("searchText");

        var additionalData = req.getParameter("additionalData");

        var holder = CriteriaHolder.getQueryHolder(session, Course.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(pageValue)) {
            addPageValueCondition(pageValue, holder, predicates);
        }

        if (StringUtils.isNotBlank(searchText)) {
            addSearchCondition(searchText, holder, predicates);
        }

        if (StringUtils.isNotBlank(additionalData)) {
            var additionalDataJson = new JsonParser()
                    .parse(additionalData)
                    .getAsJsonObject();

            processAdditionalData(holder, predicates, additionalDataJson);
        }

        var graph = session.createEntityGraph(Course.class);
        graph.addAttributeNodes(Course_.DEPARTMENT);

        holder.getQuery()
                .where(holder.getBuilder().and(predicates.toArray(new Predicate[0])))
                .orderBy(holder.getBuilder().asc(holder.getRoot().get(Course_.name)));

        var results = session.createQuery(holder.getQuery())
                .applyLoadGraph(graph)
                .setMaxResults(super.getPageSize() + 1)
                .getResultList();

        var output = super.getSuccessJson();

        if (results.size() == super.getPageSize() + 1) {
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
                gson.toJson(output)
        );
    }

    private void addPageValueCondition(String pageValue, CriteriaHolder<CriteriaQuery<Course>, Course, Course> holder, List<Predicate> predicates) {
        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(Course_.name), pageValue)
        );
    }

    private void addSearchCondition(String searchText, CriteriaHolder<CriteriaQuery<Course>, Course, Course> holder, List<Predicate> predicates) {
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

    private void processAdditionalData(CriteriaHolder<CriteriaQuery<Course>, Course, Course> holder, List<Predicate> predicates, JsonObject additionalDataJson) {
        // if the additional data contains the departmentId then get the courses of the department.
        Optional.ofNullable(additionalDataJson.get("departmentId"))
                .map(JsonElement::getAsLong)
                .ifPresent(departmentId -> addDepartmentCondition(holder, predicates, additionalDataJson, departmentId));
    }

    private void addDepartmentCondition(CriteriaHolder<CriteriaQuery<Course>, Course, Course> holder, List<Predicate> predicates, JsonObject additionalDataJson, Long departmentId) {

        Join<Course, Department> departmentJoin = holder.getRoot()
                .getJoins()
                .stream()
                .filter(courseJoin -> courseJoin.getAttribute().equals(Course_.department))
                .map(courseJoin -> (Join<Course, Department>) courseJoin)
                .findFirst()
                .orElse(null);

        //if the join does not exist already then add it other wise use the added join
        if (Objects.isNull(departmentJoin)) {
            departmentJoin = holder.getRoot()
                    .join(Course_.department, JoinType.INNER);
        }

        var condition = holder.getBuilder()
                .equal(departmentJoin.get(Department_.id), departmentId);
        predicates.add(condition);
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

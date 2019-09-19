package admin.ajax;

import com.google.gson.*;
import entities.Course;
import entities.Course_;
import entities.Department;
import entities.Department_;
import org.hibernate.Session;
import utility.AjaxController;
import utility.CriteriaHolder;

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

        addPageValueCondition(pageValue, holder, predicates);
        addSearchCondition(searchText, holder, predicates);
        processAdditionalData(holder, predicates, additionalData);

        var courses = getCourses(session, holder, predicates);

        var output = super.getSuccessJson();

        addDataToJson(output, courses);

        out.println(
                gson.toJson(output)
        );
    }

    private void addPageValueCondition(String pageValue, CriteriaHolder<CriteriaQuery<Course>, Course> holder, List<Predicate> predicates) {

        if (pageValue.isBlank()) {
            return;
        }

        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(Course_.name), pageValue)
        );
    }

    private void addSearchCondition(String searchText, CriteriaHolder<CriteriaQuery<Course>, Course> holder, List<Predicate> predicates) {

        if (searchText.isBlank()) {
            return;
        }

        var departmentJoinQuery = getDepartmentJoin(holder);

        predicates.add(
                holder.getBuilder().or(
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(holder.getRoot().get(Course_.name)), searchText.toLowerCase() + "%"),
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(departmentJoinQuery.get(Department_.name)), searchText.toLowerCase() + "%")
                )
        );
    }


    private Join<Course, Department> getDepartmentJoin(CriteriaHolder<CriteriaQuery<Course>, Course> holder) {
        return holder.getJoin(Course_.department)
                .orElseGet(() -> joinDepartment(holder));
    }

    private Join<Course, Department> joinDepartment(CriteriaHolder<CriteriaQuery<Course>, Course> holder) {
        return holder.getRoot()
                .join(Course_.department, JoinType.INNER);
    }

    private void processAdditionalData(CriteriaHolder<CriteriaQuery<Course>, Course> holder, List<Predicate> predicates, String additionalData) {

        JsonObject additionalDataJson = parseAdditionalData(additionalData);

        // if the additional data contains the departmentId then get the courses of the department.
        Optional.ofNullable(additionalDataJson.get("departmentId"))
                .map(JsonElement::getAsLong)
                .ifPresent(departmentId -> addDepartmentCondition(holder, predicates, additionalDataJson, departmentId));
    }

    private JsonObject parseAdditionalData(String additionalData) {
        try {
            return new JsonParser()
                    .parse(additionalData)
                    .getAsJsonObject();
        } catch (Exception e) {
            return new JsonObject();
        }
    }


    private void addDepartmentCondition(CriteriaHolder<CriteriaQuery<Course>, Course> holder, List<Predicate> predicates, JsonObject additionalDataJson, Long departmentId) {

        Join<Course, Department> departmentJoin = getDepartmentJoin(holder);

        var condition = holder.getBuilder()
                .equal(departmentJoin.get(Department_.id), departmentId);
        predicates.add(condition);
    }

    private List<Course> getCourses(Session session, CriteriaHolder<CriteriaQuery<Course>, Course> holder, List<Predicate> predicates) {
        var graph = session.createEntityGraph(Course.class);
        graph.addAttributeNodes(Course_.DEPARTMENT);

        holder.getQuery()
                .where(holder.getBuilder().and(predicates.toArray(new Predicate[0])))
                .orderBy(holder.getBuilder().asc(holder.getRoot().get(Course_.name)));

        return session.createQuery(holder.getQuery())
                .applyLoadGraph(graph)
                .setMaxResults(super.getPageSize() + 1)
                .getResultList();
    }

    private void addDataToJson(JsonObject successJson, List<Course> courses) {

        setMorePageProperty(successJson, courses);
        removeExtraCourse(courses);
        setPageValueProperty(successJson, courses);

        JsonArray data = courses.stream()
                .map(this::mapCourseToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        successJson.add(DATA, data);
    }


    private void setMorePageProperty(JsonObject successJson, List<Course> courses) {
        successJson.addProperty("more", courses.size() == super.getPageSize() + 1);
    }

    private void removeExtraCourse(List<Course> courses) {
        if (courses.size() == super.getPageSize() + 1) {
            courses.remove(courses.size() - 1);
        }
    }

    private void setPageValueProperty(JsonObject successJson, List<Course> courses) {
        if (courses.size() > 0) {
            successJson.addProperty("pageValue", courses.get(courses.size() - 1).getName());
        } else {
            successJson.addProperty("pageValue", "");
        }
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

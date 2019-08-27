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


@WebServlet(urlPatterns = "/admin/ajax/get-classrooms")
public class GetClassRooms extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        Gson gson = new Gson();

        var pageValue = req.getParameter("pageValue");
        var searchText = req.getParameter("searchText");

        var additionalData = req.getParameter("additionalData");

        var holder = CriteriaHolder.getQueryHolder(session, ClassRoom.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(pageValue)) {
            addPageValueCondition(pageValue, holder, predicates);
        }

        if (StringUtils.isNotBlank(searchText)) {
            addSearchTextCondition(searchText, holder, predicates);
        }

        if (StringUtils.isNotBlank(additionalData)) {
            var additionalDataJson = new JsonParser()
                    .parse(additionalData)
                    .getAsJsonObject();

            processAdditionalData(holder, predicates, additionalDataJson);
        }

        var graph = session.createEntityGraph(ClassRoom.class);
        graph.addAttributeNodes(ClassRoom_.COURSE);

        holder.getQuery()
                .where(holder.getBuilder().and(predicates.toArray(new Predicate[0])))
                .orderBy(holder.getBuilder().asc(holder.getRoot().get(ClassRoom_.name)));

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
                .map(this::mapClassRoomToJson)
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

    private void addSearchTextCondition(String searchText, CriteriaHolder<ClassRoom, CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates) {
        var courseJoinQuery = holder.getRoot()
                .join(ClassRoom_.course, JoinType.INNER);

        predicates.add(
                holder.getBuilder().or(
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(holder.getRoot().get(ClassRoom_.name)), searchText.toLowerCase() + "%"),
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(courseJoinQuery.get(Course_.name)), searchText.toLowerCase() + "%")
                )
        );
    }

    private void addPageValueCondition(String pageValue, CriteriaHolder<ClassRoom, CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates) {
        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(ClassRoom_.name), pageValue)
        );
    }

    private void processAdditionalData(CriteriaHolder<ClassRoom, CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates, JsonObject additionalDataJson) {
        Optional.of(additionalDataJson.get("courseId"))
                .map(JsonElement::getAsLong)
                .ifPresent(courseId -> addCourseCondition(holder, predicates, additionalDataJson, courseId));

    }

    private void addCourseCondition(CriteriaHolder<ClassRoom, CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates, JsonObject additionalDataJson, Long courseId) {
        Join<ClassRoom, Course> courseJoinQuery = holder.getRoot()
                .getJoins()
                .stream()
                .filter(classRoomJoin -> classRoomJoin.getAttribute().equals(ClassRoom_.course))
                .map(classRoomJoin -> (Join<ClassRoom, Course>) classRoomJoin)
                .findFirst()
                .orElse(null);

        if (Objects.isNull(courseJoinQuery)) {
            courseJoinQuery = holder.getRoot()
                    .join(ClassRoom_.course, JoinType.INNER);
        }

        var condition = holder.getBuilder()
                .equal(courseJoinQuery.get(Course_.id), courseId);
        predicates.add(condition);
    }

    private JsonObject mapClassRoomToJson(ClassRoom classRoom) {
        var jsonObject = new JsonObject();

        jsonObject.addProperty("id", classRoom.getId());
        jsonObject.addProperty("name", classRoom.getName());
        jsonObject.addProperty("division", classRoom.getDivision());
        jsonObject.addProperty("semester", classRoom.getSemester());
        jsonObject.addProperty("minimumSubjects", classRoom.getMinimumSubjects());
        jsonObject.addProperty("courseId", classRoom.getCourse().getId());
        jsonObject.addProperty("courseName", classRoom.getCourse().getName());

        return jsonObject;
    }
}

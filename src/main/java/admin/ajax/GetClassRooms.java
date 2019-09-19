package admin.ajax;

import com.google.gson.*;
import entities.ClassRoom;
import entities.ClassRoom_;
import entities.Course;
import entities.Course_;
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

        addPageValueCondition(pageValue, holder, predicates);
        addSearchTextCondition(searchText, holder, predicates);
        processAdditionalData(holder, predicates, additionalData);

        var classRooms = getClassRooms(session, holder, predicates);

        var successJson = super.getSuccessJson();
        addDataToJson(successJson, classRooms);

        out.println(
                gson.toJson(successJson)
        );
    }

    private void addSearchTextCondition(String searchText, CriteriaHolder<CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates) {

        if (searchText.isBlank()) {
            return;
        }

        Join<ClassRoom, Course> courseJoinQuery = getCourseJoin(holder);

        predicates.add(
                holder.getBuilder().or(
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(holder.getRoot().get(ClassRoom_.name)), searchText.toLowerCase() + "%"),
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(courseJoinQuery.get(Course_.name)), searchText.toLowerCase() + "%")
                )
        );
    }

    private Join<ClassRoom, Course> getCourseJoin(CriteriaHolder<CriteriaQuery<ClassRoom>, ClassRoom> holder) {
        return holder.getJoin(ClassRoom_.course)
                .orElseGet(() -> joinCourse(holder));
    }

    private Join<ClassRoom, Course> joinCourse(CriteriaHolder<CriteriaQuery<ClassRoom>, ClassRoom> holder) {
        return holder.getRoot()
                .join(ClassRoom_.course, JoinType.INNER);
    }


    private void addPageValueCondition(String pageValue, CriteriaHolder<CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates) {

        if (pageValue.isBlank()) {
            return;
        }

        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(ClassRoom_.name), pageValue)
        );

    }

    private void processAdditionalData(CriteriaHolder<CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates, String additionalData) {

        JsonObject additionalDataJson = parseAdditionalData(additionalData);

        Optional.of(additionalDataJson.get("courseId"))
                .map(JsonElement::getAsLong)
                .ifPresent(courseId -> addCourseCondition(holder, predicates, courseId));

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


    private void addCourseCondition(CriteriaHolder<CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates, Long courseId) {
        Join<ClassRoom, Course> courseJoinQuery = getCourseJoin(holder);

        var condition = holder.getBuilder()
                .equal(courseJoinQuery.get(Course_.id), courseId);
        predicates.add(condition);
    }

    private List<ClassRoom> getClassRooms(Session session, CriteriaHolder<CriteriaQuery<ClassRoom>, ClassRoom> holder, List<Predicate> predicates) {
        // add course graph
        var graph = session.createEntityGraph(ClassRoom.class);
        graph.addAttributeNodes(ClassRoom_.COURSE);

        holder.getQuery()
                .where(holder.getBuilder().and(predicates.toArray(new Predicate[0])))
                .orderBy(holder.getBuilder().asc(holder.getRoot().get(ClassRoom_.name)));

        return session.createQuery(holder.getQuery())
                .applyLoadGraph(graph)
                .setMaxResults(super.getPageSize() + 1)
                .getResultList();
    }

    private void addDataToJson(JsonObject successJson, List<ClassRoom> classRooms) {

        setMorePageProperty(successJson, classRooms);
        removeExtraClassRoom(classRooms);
        setPageValueProperty(successJson, classRooms);

        JsonArray classRoomsJson = classRooms.stream()
                .map(this::mapClassRoomToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        successJson.add(DATA, classRoomsJson);
    }

    private void setMorePageProperty(JsonObject successJson, List<ClassRoom> classRooms) {
        successJson.addProperty("more", classRooms.size() == super.getPageSize() + 1);
    }

    private void removeExtraClassRoom(List<ClassRoom> classRooms) {
        if (classRooms.size() == super.getPageSize() + 1) {
            classRooms.remove(classRooms.size() - 1);
        }
    }

    private void setPageValueProperty(JsonObject successJson, List<ClassRoom> classRooms) {
        if (classRooms.size() > 0) {
            successJson.addProperty("pageValue", classRooms.get(classRooms.size() - 1).getName());
        } else {
            successJson.addProperty("pageValue", "");
        }
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
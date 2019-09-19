package admin.ajax;

import com.google.gson.*;
import entities.*;
import org.apache.commons.lang3.StringUtils;
import org.gradle.internal.impldep.com.google.api.client.json.Json;
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
import java.util.UUID;

@WebServlet(urlPatterns = {"/admin/ajax/get-teachers", "/test"})
public class GetTeachers extends AjaxController {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        //the pageValue is split by - "firstName--lastName"
        var pageValue = req.getParameter("pageValue");
        var searchText = req.getParameter("searchText");
        var additionalData = req.getParameter("additionalData");

        var holder = CriteriaHolder.getQueryHolder(session, Teacher.class);

        List<Predicate> predicates = new ArrayList<>();


        addSearchConditions(searchText.trim(), holder, predicates);
        addPageValueCondition(pageValue, holder, predicates);
        processAdditionalData(holder, predicates, additionalData);

        var teachers = getTeachers(session, holder, predicates);

        var output = super.getSuccessJson();

        addDataToJson(output, teachers);

        out.println(new Gson().toJson(output));
    }

    private Join<Teacher, User> getUserJoin(CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder) {
        return holder.getJoin(Teacher_.user)
                .orElseGet(() -> joinUser(holder));
    }

    private Join<Teacher, User> joinUser(CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder) {
        return holder.getRoot()
                .join(Teacher_.user, JoinType.INNER);
    }


    private void addPageValueCondition(String pageValue, CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder, List<Predicate> predicates) {
        if (pageValue.isBlank()) {
            return;
        }

        String[] names = pageValue.split("--");
        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(Teacher_.fName), names[0])
        );
        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(Teacher_.lName), names[1])
        );
    }


    private void processAdditionalData(CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder, List<Predicate> predicates, String additionalData) {

        JsonObject additionalDataJson = parseAdditionData(additionalData);

        // if the additional data contains the departmentId then get the teacher of the department.
        Optional.ofNullable(additionalDataJson.get("departmentId"))
                .map(JsonElement::getAsLong)
                .ifPresent(departmentId -> addDepartmentCondition(holder, predicates, additionalDataJson, departmentId));

    }

    private JsonObject parseAdditionData(String additionalData) {
        try {
            return new JsonParser()
                    .parse(additionalData)
                    .getAsJsonObject();
        } catch (Exception e) {
            return new JsonObject();
        }
    }


    private void addDepartmentCondition(CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder, List<Predicate> predicates, JsonObject additionalDataJson, Long departmentId) {

        var subQuery = holder.getQuery()
                .subquery(UUID.class);
        var subQueryRoot = subQuery.from(TeacherDepartmentLink.class);

        subQuery.where(
                holder.getBuilder().equal(subQueryRoot.get(TeacherDepartmentLink_.department), departmentId)
        );

        var innerJoin = subQueryRoot.join(TeacherDepartmentLink_.teacher, JoinType.INNER);
        subQuery.select(innerJoin.get(Teacher_.id));

        predicates.add(
                holder.getRoot().get(Teacher_.id).in(subQuery)
        );


    }

    private List<Teacher> getTeachers(Session session, CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder, List<Predicate> predicates) {

        var graph = session.createEntityGraph(Teacher.class);
        graph.addAttributeNode(Teacher_.user);

        holder.getQuery()
                .where(
                        holder.getBuilder()
                                .and(predicates.toArray(new Predicate[0]))
                );

        holder.getQuery()
                .orderBy(
                        holder.getBuilder()
                                .asc(holder.getRoot().get(Teacher_.fName)),
                        holder.getBuilder()
                                .asc(holder.getRoot().get(Teacher_.lName))
                );

        return session.createQuery(holder.getQuery())
                .setMaxResults(super.getPageSize() + 1)
                .setReadOnly(true)
                .applyLoadGraph(graph)
                .getResultList();
    }


    private void addDataToJson(JsonObject successJson, List<Teacher> teachers) {

        setMorePageProperty(successJson, teachers);
        removeExtraTeacher(teachers);
        setPageValueProperty(successJson, teachers);

        JsonArray data = teachers.stream()
                .map(this::mapTeacherToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        successJson.add(DATA, data);

    }

    private void setMorePageProperty(JsonObject successJson, List<Teacher> teachers) {
        successJson.addProperty("more", teachers.size() == super.getPageSize() + 1);
    }

    private void removeExtraTeacher(List<Teacher> teachers) {
        if (teachers.size() == super.getPageSize() + 1) {
            teachers.remove(teachers.size() - 1);
        }
    }

    private void setPageValueProperty(JsonObject successJson, List<Teacher> teachers) {
        if (teachers.size() > 0) {
            var teacher = teachers.get(teachers.size() - 1);
            successJson.addProperty("pageValue", teacher.getFName() + "--" + teacher.getLName());
        } else {
            successJson.addProperty("pageValue", "");
        }
    }


    private JsonObject mapTeacherToJson(Teacher teacher) {
        var jsonObject = new JsonObject();

        jsonObject.addProperty("id", teacher.getId().toString());
        jsonObject.addProperty("name", teacher.getFName() + " " + teacher.getLName());
        jsonObject.addProperty("username", teacher.getUser().getUsername());
        jsonObject.addProperty("email", teacher.getUser().getEmail());
        jsonObject.addProperty("status", teacher.getUser().getStatus().toString());

        return jsonObject;

    }

    private void addSearchConditions(String searchText, CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder, List<Predicate> predicates) {

        if (searchText.isBlank()) {
            return;
        }

        List<Predicate> orPredicates = new ArrayList<>();

        Join<Teacher, User> userJoin = getUserJoin(holder);

        orPredicates.add(
                holder.getBuilder()
                        .like(holder.getBuilder().lower(userJoin.get(User_.email)), searchText.toLowerCase() + "%")
        );

        orPredicates.add(
                holder.getBuilder()
                        .like(holder.getBuilder().lower(holder.getRoot().get(Teacher_.fName)), searchText.toLowerCase() + "%")
        );

        orPredicates.add(
                holder.getBuilder()
                        .like(holder.getBuilder().lower(holder.getRoot().get(Teacher_.lName)), searchText.toLowerCase() + "%")
        );

        //adds user status search condition
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.toString()
                    .toLowerCase()
                    .contains(searchText)) {

                orPredicates.add(
                        holder.getBuilder()
                                .equal(userJoin.get(User_.status), userStatus)
                );

            }
        }

        //adds search by number condition
        if (StringUtils.isNumeric(searchText)) {
            var number = Long.parseLong(searchText);
            number = number * (long) Math.pow(10, (10 - searchText.trim().length()));

            orPredicates.add(
                    holder.getBuilder()
                            .greaterThanOrEqualTo(userJoin.get(User_.number), number)
            );
        }

        predicates.add(
                holder.getBuilder()
                        .or(orPredicates.toArray(new Predicate[0]))
        );
    }


}

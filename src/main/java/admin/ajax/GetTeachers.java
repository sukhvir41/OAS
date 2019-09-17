package admin.ajax;

import com.google.gson.*;
import entities.*;
import org.apache.commons.lang3.StringUtils;
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

        var userJoin = holder.getRoot()
                .join(Teacher_.user, JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(searchText)) {
            addSearchConditions(searchText.trim(), holder, userJoin, predicates);
        }

        if (StringUtils.isNotBlank(pageValue)) {
            addPageValueCondition(pageValue, holder, predicates);
        }

        if (StringUtils.isNotBlank(additionalData)) {
            var additionalDataJson = new JsonParser()
                    .parse(additionalData)
                    .getAsJsonObject();

            processAdditionalData(holder, predicates, additionalDataJson);
        }

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

        var graph = session.createEntityGraph(Teacher.class);
        graph.addAttributeNode(Teacher_.user);

        var results = session.createQuery(holder.getQuery())
                .setMaxResults(super.getPageSize() + 1)
                .setReadOnly(true)
                .applyLoadGraph(graph)
                .getResultList();

        var output = super.getSuccessJson();

        if (results.size() == super.getPageSize() + 1) {
            output.addProperty("more", true);
            results.remove(results.size() - 1);
        } else {
            output.addProperty("more", false);
        }

        JsonArray data = results.stream()
                .map(this::mapTeacherToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        output.add(DATA, data);
        if (data.size() > 0) {
            var teacher = results.get(results.size() - 1);
            output.addProperty("pageValue", teacher.getFName() + "--" + teacher.getLName());
        } else {
            output.addProperty("pageValue", "");
        }

        out.println(new Gson().toJson(output));
    }

    private void addPageValueCondition(String pageValue, CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder, List<Predicate> predicates) {
        var names = pageValue.split("--");
        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(Teacher_.fName), names[0])
        );
        predicates.add(
                holder.getBuilder()
                        .greaterThan(holder.getRoot().get(Teacher_.lName), names[1])
        );
    }

    private void processAdditionalData(CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder, List<Predicate> predicates, JsonObject additionalDataJson) {
        // if the additional data contains the departmentId then get the teacher of the department.
        Optional.ofNullable(additionalDataJson.get("departmentId"))
                .map(JsonElement::getAsLong)
                .ifPresent(departmentId -> addDepartmentCondition(holder, predicates, additionalDataJson, departmentId));

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

    private JsonObject mapTeacherToJson(Teacher teacher) {
        var jsonObject = new JsonObject();

        jsonObject.addProperty("id", teacher.getId().toString());
        jsonObject.addProperty("name", teacher.getFName() + " " + teacher.getLName());
        jsonObject.addProperty("username", teacher.getUser().getUsername());
        jsonObject.addProperty("email", teacher.getUser().getEmail());
        jsonObject.addProperty("status", teacher.getUser().getStatus().toString());

        return jsonObject;

    }

    private void addSearchConditions(String searchText, CriteriaHolder<CriteriaQuery<Teacher>, Teacher> holder, Join<Teacher, User> userJoin, List<Predicate> predicates) {
        List<Predicate> orPredicates = new ArrayList<>();

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

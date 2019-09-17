package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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


@WebServlet(urlPatterns = "/admin/ajax/get-admins")
public class GetAdmins extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var pageValue = req.getParameter("pageValue");
        var searchText = req.getParameter("searchText");

        var holder = CriteriaHolder.getQueryHolder(session, Admin.class);
        List<Predicate> predicates = new ArrayList<>();

        addUserJoin(holder);
        addPageValueCondition(pageValue, holder, predicates);
        addSearchCondition(searchText, holder, predicates);

        var adminList = getAdminsList(session, holder, predicates);

        var successJson = super.getSuccessJson();

        setMorePageAttributeAndRemoveLastAdmin(adminList, successJson);

        JsonArray data = getAdminAsJsonData(adminList);

        successJson.add(DATA, data);

        setPageValue(adminList, successJson);

        out.println(
                new Gson().toJson(successJson)
        );
    }

    private void setPageValue(List<Admin> adminList, JsonObject successJsonObject) {
        if (adminList.size() > 0) {
            successJsonObject
                    .addProperty("pageValue",
                            adminList.get(adminList.size() - 1)
                                    .getUser()
                                    .getUsername()
                    );
        } else {
            successJsonObject.addProperty("pageValue", "");
        }
    }

    private JsonArray getAdminAsJsonData(List<Admin> adminList) {
        return adminList.stream()
                .map(this::mapAdminToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
    }

    private void setMorePageAttributeAndRemoveLastAdmin(List<Admin> adminList, JsonObject successJsonObject) {
        if (adminList.size() == super.getPageSize() + 1) {
            successJsonObject.addProperty("more", true);
            adminList.remove(adminList.size() - 1);
        } else {
            successJsonObject.addProperty("more", false);
        }
    }

    private List<Admin> getAdminsList(Session session, CriteriaHolder<CriteriaQuery<Admin>, Admin> holder, List<Predicate> predicates) {

        var graph = session.createEntityGraph(Admin.class);
        graph.addAttributeNodes(Admin_.USER);

        holder.getQuery()
                .where(
                        holder.getBuilder()
                                .and(predicates.toArray(new Predicate[0]))
                )
                .orderBy(
                        holder.getBuilder()
                                .asc(getUserJoin(holder).get(User_.username))
                );

        return session.createQuery(holder.getQuery())
                .applyLoadGraph(graph)
                .setMaxResults(getPageSize() + 1)
                .getResultList();
    }


    private void addSearchCondition(String searchText, CriteriaHolder<CriteriaQuery<Admin>, Admin> holder, List<Predicate> predicates) {
        if (StringUtils.isNotBlank(searchText)) {

            Join<Admin, User> userJoin = getUserJoin(holder);

            var searchPredicate = holder.getBuilder()
                    .or(
                            holder.getBuilder()
                                    .like(holder.getBuilder().lower(userJoin.get(User_.username)), searchText.toLowerCase() + "%"),
                            holder.getBuilder()
                                    .like(holder.getBuilder().lower(userJoin.get(User_.email)), searchText.toLowerCase() + "%"),
                            holder.getBuilder()
                                    .like(holder.getBuilder().lower(holder.getRoot().get(Admin_.type)), searchText.toLowerCase() + "%")
                    );
            predicates.add(searchPredicate);
        }
    }

    private void addPageValueCondition(String pageValue, CriteriaHolder<CriteriaQuery<Admin>, Admin> holder, List<Predicate> predicates) {

        if (StringUtils.isNotBlank(pageValue)) {
            predicates.add(
                    holder.getBuilder()
                            .greaterThan(getUserJoin(holder).get(User_.username), pageValue)
            );
        }
    }

    private JsonObject mapAdminToJson(Admin admin) {
        var jsonObject = new JsonObject();

        jsonObject.addProperty("id", admin.getId().toString());
        jsonObject.addProperty("username", admin.getUser().getUsername());
        jsonObject.addProperty("type", admin.getType().toString());
        jsonObject.addProperty("email", admin.getUser().getEmail());

        return jsonObject;
    }

    private Join<Admin, User> getUserJoin(CriteriaHolder<CriteriaQuery<Admin>, Admin> holder) {
        return holder.getRoot()
                .getJoins()
                .stream()
                .filter(join -> join.getAttribute().equals(Admin_.user))
                .map(join -> (Join<Admin, User>) join)
                .findFirst()
                .get();
    }

    private void addUserJoin(CriteriaHolder<CriteriaQuery<Admin>, Admin> holder) {
        holder.getRoot()
                .join(Admin_.user, JoinType.INNER);
    }
}

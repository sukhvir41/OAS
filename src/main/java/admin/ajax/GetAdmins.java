package admin.ajax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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


@WebServlet(urlPatterns = "/admin/ajax/get-admins")
public class GetAdmins extends AjaxController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, PrintWriter out) throws Exception {

        var pageValue = req.getParameter("pageValue");
        var searchText = req.getParameter("searchText");

        var holder = CriteriaHolder.getQueryHolder(session, Admin.class);
        var userJoin = holder.getRoot()
                .join(Admin_.user, JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(pageValue)) {
            addPageValueCondition(pageValue, holder, userJoin, predicates);
        }

        if (StringUtils.isNotBlank(searchText)) {
            addSearchCondition(searchText, holder, userJoin, predicates);
        }

        var graph = session.createEntityGraph(Admin.class);
        graph.addAttributeNodes(Admin_.USER);

        holder.getQuery()
                .where(holder.getBuilder().and(predicates.toArray(new Predicate[0])))
                .orderBy(holder.getBuilder().asc(userJoin.get(User_.username)));

        var results = session.createQuery(holder.getQuery())
                .applyLoadGraph(graph)
                .setMaxResults(getPageSize() + 1)
                .getResultList();

        var output = super.getSuccessJson();

        if (results.size() == super.getPageSize() + 1) {
            output.addProperty("more", true);
            results.remove(results.size() - 1);
        } else {
            output.addProperty("more", false);
        }

        JsonArray data = results.stream()
                .map(this::mapAdminToJson)
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

        output.add(DATA, data);
        if (data.size() > 0) {
            output.addProperty("pageValue", results.get(results.size() - 1).getUser().getUsername());
        } else {
            output.addProperty("pageValue", "");
        }

        out.println(
                new Gson().toJson(output)
        );

    }

    private void addSearchCondition(String searchText, CriteriaHolder<CriteriaQuery<Admin>, Admin, Admin> holder, Join<Admin, User> userJoin, List<Predicate> predicates) {
        predicates.add(
                holder.getBuilder().or(
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(userJoin.get(User_.username)), searchText.toLowerCase() + "%"),
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(userJoin.get(User_.email)), searchText.toLowerCase() + "%"),
                        holder.getBuilder()
                                .like(holder.getBuilder().lower(holder.getRoot().get(Admin_.type)), searchText.toLowerCase() + "%")
                )
        );
    }

    private void addPageValueCondition(String pageValue, CriteriaHolder<CriteriaQuery<Admin>, Admin, Admin> holder, Join<Admin, User> userJoin, List<Predicate> predicates) {
        predicates.add(
                holder.getBuilder()
                        .greaterThan(userJoin.get(User_.username), pageValue)
        );
    }

    private JsonObject mapAdminToJson(Admin admin) {
        var jsonObject = new JsonObject();

        jsonObject.addProperty("id", admin.getId().toString());
        jsonObject.addProperty("username", admin.getUser().getUsername());
        jsonObject.addProperty("type", admin.getType().toString());
        jsonObject.addProperty("email", admin.getUser().getEmail());

        return jsonObject;

    }
}

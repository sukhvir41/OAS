package utility;

import entities.Admin;
import entities.User;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Optional;

public class CriteriaHolder<QueryType, RootType> {
    CriteriaBuilder builder;
    QueryType query;
    Root<RootType> root;

    public static <theClassType> CriteriaHolder<CriteriaUpdate<theClassType>, theClassType> getUpdateHolder(Session session, Class<theClassType> theClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<theClassType> query = builder.createCriteriaUpdate(theClass);
        Root<theClassType> root = query.from(theClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public static <theClassType> CriteriaHolder<CriteriaQuery<theClassType>, theClassType> getQueryHolder(Session session, Class<theClassType> theClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<theClassType> query = builder.createQuery(theClass);
        Root<theClassType> root = query.from(theClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public static <QueryClassType, RootClassType> CriteriaHolder<CriteriaQuery<QueryClassType>, RootClassType> getQueryHolder(Session session, Class<QueryClassType> queryClass, Class<RootClassType> rootClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<QueryClassType> query = builder.createQuery(queryClass);
        Root<RootClassType> root = query.from(rootClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public static <TheClassType> CriteriaHolder<CriteriaDelete<TheClassType>, TheClassType> getDeleteHolder(Session session, Class<TheClassType> theClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<TheClassType> query = builder.createCriteriaDelete(theClass);
        Root<TheClassType> root = query.from(theClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public CriteriaHolder(CriteriaBuilder criteriaBuilder, QueryType query, Root<RootType> root) {
        this.builder = criteriaBuilder;
        this.query = query;
        this.root = root;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return builder;
    }

    public CriteriaBuilder getBuilder() {
        return builder;
    }

    public QueryType getQuery() {
        return query;
    }

    public Root<RootType> getRoot() {
        return root;
    }

    public <A> Path<A> getPath(SingularAttribute<RootType, A> attribute) {
        return root.get(attribute);
    }

    public <VariableType> boolean joinExists(Attribute<? super RootType, VariableType> joinedAttribute) {
        return getRoot()
                .getJoins()
                .stream()
                .filter(join -> join.getAttribute().equals(joinedAttribute))
                .anyMatch(ObjectUtils::isNotEmpty);
    }

    public <VariableType> Optional<Join<RootType, VariableType>> getJoin(Attribute<RootType, VariableType> joinedAttribute) {
        return getRoot()
                .getJoins()
                .stream()
                .filter(join -> join.getAttribute().equals(joinedAttribute))
                .map(join -> (Join<RootType, VariableType>) join)
                .findFirst();
    }
}



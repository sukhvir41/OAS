package entities;

import org.hibernate.Session;

import javax.persistence.criteria.*;

public class CriteriaHolder<U, T> {
    CriteriaBuilder criteriaBuilder;
    U query;
    Root<T> root;

    public static <T> CriteriaHolder<CriteriaUpdate<T>, T> getUpdateHolder(Session session, Class<T> tClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<T> query = builder.createCriteriaUpdate(tClass);
        Root<T> root = query.from(tClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public static <T> CriteriaHolder<CriteriaQuery<T>, T> getQueryHolder(Session session, Class<T> tClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> root = query.from(tClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public static <T> CriteriaHolder<CriteriaDelete<T>, T> getDeleteHolder(Session session, Class<T> tClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<T> query = builder.createCriteriaDelete(tClass);
        Root<T> root = query.from(tClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public CriteriaHolder(CriteriaBuilder criteriaBuilder, U query, Root<T> root) {
        this.criteriaBuilder = criteriaBuilder;
        this.query = query;
        this.root = root;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public U getQuery() {
        return query;
    }

    public Root<T> getRoot() {
        return root;
    }
}

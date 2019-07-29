package entities;

import org.hibernate.Session;

import javax.persistence.criteria.*;

public class CriteriaHolder<U, T, V> {
    CriteriaBuilder builder;
    U query;
    Root<V> root;

    public static <T> CriteriaHolder<CriteriaUpdate<T>, T, T> getUpdateHolder(Session session, Class<T> tClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<T> query = builder.createCriteriaUpdate(tClass);
        Root<T> root = query.from(tClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public static <T> CriteriaHolder<CriteriaQuery<T>, T, T> getQueryHolder(Session session, Class<T> tClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> root = query.from(tClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public static <T, V> CriteriaHolder<CriteriaQuery<T>, T, V> getQueryHolder(Session session, Class<T> queryClass, Class<V> rootClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(queryClass);
        Root<V> root = query.from(rootClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public static <T> CriteriaHolder<CriteriaDelete<T>, T, T> getDeleteHolder(Session session, Class<T> tClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<T> query = builder.createCriteriaDelete(tClass);
        Root<T> root = query.from(tClass);
        return new CriteriaHolder<>(builder, query, root);
    }

    public CriteriaHolder(CriteriaBuilder criteriaBuilder, U query, Root<V> root) {
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

    public U getQuery() {
        return query;
    }

    public Root<V> getRoot() {
        return root;
    }
}

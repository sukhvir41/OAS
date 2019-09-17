package entities;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;
import utility.CriteriaHolder;
import utility.Utils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EntityHelper {

    private EntityHelper() throws InstantiationException {
        throw new InstantiationException("Cant not create object of this class");
    }

    public static <T, U> void deleteInstance(U id, SingularAttribute<T, U> idField, Class<T> tClass, Session session) throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaDelete<T> delete = builder.createCriteriaDelete(tClass);
        Root<T> classRoot = delete.from(tClass);

        delete.where(builder.equal(classRoot.get(idField), id));

        session.createQuery(delete)
                .executeUpdate();
    }

    public static <T> List<T> getAll(Session session, Class<T> tClass, boolean readOnly) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> classRoot = query.from(tClass);

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .getResultList();

    }

    public static <T> List<T> getAll(Session session, Class<T> tClass, RootGraph<T> graph, boolean readOnly) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> classRoot = query.from(tClass);

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .applyLoadGraph(graph)
                .getResultList();

    }

    public static <T, U> List<T> getAll(Session session, Class<T> tClass, SingularAttribute<T, U> orderBy, EntityGraph<T> graph, boolean readOnly) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> classRoot = query.from(tClass);

        query.orderBy(builder.asc(classRoot.get(orderBy)));

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .setHint(Utils.LOAD_ENTITY_HINT, graph)
                .getResultList();

    }


    public static <T, U> List<T> getAll(Session session, Class<T> tClass, SingularAttribute<T, U> orderBy, boolean readOnly) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> classRoot = query.from(tClass);

        query.orderBy(builder.asc(classRoot.get(orderBy)));

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .getResultList();

    }


    public static <U, T> T getInstance(U id, SingularAttribute<T, U> idField, Class<T> aClass, Session session, boolean readOnly, String... fieldNames) {
        EntityGraph<T> graph = session.createEntityGraph(aClass);
        graph.addAttributeNodes(fieldNames);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(aClass);
        Root<T> departmentRoot = query.from(aClass);
        query.where(
                builder.equal(departmentRoot.get(idField), id)
        );

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .setHint(Utils.LOAD_ENTITY_HINT, graph)
                .getSingleResult();
    }

    public static <U, T> T getInstance(U id, SingularAttribute<T, U> idField, Class<T> tClass, Session session, boolean readOnly) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> departmentRoot = query.from(tClass);
        query.where(
                builder.equal(departmentRoot.get(idField), id)
        );

        return session.createQuery(query)
                .setMaxResults(1)
                .setReadOnly(readOnly)
                .getSingleResult();
    }

    public static <U, T> T getFullInstance(U id, SingularAttribute<T, U> idField, Class<T> tClass, Session session, boolean readOnly) {
        var listOfFieldNames = Stream.of(tClass.getDeclaredFields())
                .filter(field -> containsAnnotations(field.getDeclaredAnnotations()))
                .map(Field::getName)
                .toArray();

        EntityGraph<T> graph = session.createEntityGraph(tClass);
        graph.addAttributeNodes((String[]) listOfFieldNames);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> departmentRoot = query.from(tClass);
        query.where(
                builder.equal(departmentRoot.get(idField), id)
        );

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .setHint(Utils.LOAD_ENTITY_HINT, graph)
                .getSingleResult();
    }

    public static <U, T> T getInstance(U id, SingularAttribute<T, U> idField, Class<T> tClass, Session session, boolean readOnly, EntityGraph<T> graph) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> departmentRoot = query.from(tClass);
        query.where(
                builder.equal(departmentRoot.get(idField), id)
        );

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .setHint(Utils.LOAD_ENTITY_HINT, graph)
                .getSingleResult();
    }


    private static boolean containsAnnotations(Annotation[] annotations) {

        var oneToOneName = OneToOne.class.getName();
        var oneToManyName = OneToMany.class.getName();
        var manyToManyName = ManyToMany.class.getName();
        var manyToOneName = ManyToOne.class.getName();


        return Stream.of(annotations)
                .anyMatch(a -> StringUtils.equalsAnyIgnoreCase(a.annotationType().getName(), oneToManyName, oneToOneName, manyToManyName, manyToOneName));
    }

    public static <T> int updateInstances(Session session, Class<T> tClass, Consumer<CriteriaHolder<CriteriaUpdate<T>, T>> operations) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<T> query = builder.createCriteriaUpdate(tClass);
        Root<T> root = query.from(tClass);

        CriteriaHolder<CriteriaUpdate<T>, T> holder = new CriteriaHolder<>(builder, query, root);

        operations.accept(holder);

        return session.createQuery(query)
                .executeUpdate();
    }

}
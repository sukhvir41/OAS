package entities;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
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

    public static <T, U> List<T> getAll(Session session, Class<T> tClass, SingularAttribute<T, U> orderBy, RootGraph<T> graph, boolean readOnly) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> classRoot = query.from(tClass);

        query.orderBy(builder.asc(classRoot.get(orderBy)));

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .applyLoadGraph(graph)
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
        RootGraph<T> graph = session.createEntityGraph(aClass);
        graph.addAttributeNodes(fieldNames);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(aClass);
        Root<T> departmentRoot = query.from(aClass);
        query.where(
                builder.equal(departmentRoot.get(idField), id)
        );

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .applyLoadGraph(graph)
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
                .setReadOnly(readOnly)
                .getSingleResult();
    }

    public static <U, T> T getFullInstance(U id, SingularAttribute<T, U> idField, Class<T> tClass, Session session, boolean readOnly) {
        var listOfFieldNames = Stream.of(tClass.getDeclaredFields())
                .filter(field -> containsAnnotations(field.getDeclaredAnnotations()))
                .map(Field::getName)
                .toArray();

        RootGraph<T> graph = session.createEntityGraph(tClass);
        graph.addAttributeNodes((String[]) listOfFieldNames);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> departmentRoot = query.from(tClass);
        query.where(
                builder.equal(departmentRoot.get(idField), id)
        );

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .applyLoadGraph(graph)
                .getSingleResult();
    }

    public static <U, T> T getInstance(U id, SingularAttribute<T, U> idField, Class<T> tClass, Session session, boolean readOnly, RootGraph<T> graph) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> departmentRoot = query.from(tClass);
        query.where(
                builder.equal(departmentRoot.get(idField), id)
        );

        return session.createQuery(query)
                .setReadOnly(readOnly)
                .applyLoadGraph(graph)
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


}
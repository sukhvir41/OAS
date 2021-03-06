package utility;

import org.hibernate.Session;
import org.hibernate.graph.RootGraph;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntitySelector<entityClass> {

    private Session session;
    private Class<entityClass> entityClass;
    private CriteriaHolder<CriteriaQuery<entityClass>, entityClass> criteriaHolder;
    private RootGraph<entityClass> graph;
    private boolean isReadOnly = false;
    private List<Predicate> predicates = new ArrayList<>();


    public static <entityClass> EntitySelector<entityClass> select(Session session, Class<entityClass> entityClass) {
        var holder = CriteriaHolder.getQueryHolder(session, entityClass);
        return new EntitySelector<>(session, entityClass, holder);
    }


    private EntitySelector(Session session, Class<entityClass> entityClass, CriteriaHolder<CriteriaQuery<entityClass>, entityClass> criteriaHolder) {
        this.session = session;
        this.entityClass = entityClass;
        this.criteriaHolder = criteriaHolder;
    }

    public EntitySelector<entityClass> getFields(RootGraph<entityClass> theGraph) {
        this.graph = theGraph;
        return this;
    }

    public EntitySelector<entityClass> getFields(String... fields) {
        var graph = session.createEntityGraph(entityClass);
        graph.addAttributeNodes(fields);

        this.graph = graph;

        return this;
    }

    public <U> EntitySelector<entityClass> orderBy(SingularAttribute<entityClass, U> orderBy) {
        criteriaHolder.getQuery()
                .orderBy(
                        criteriaHolder.getBuilder()
                                .asc(
                                        criteriaHolder.getRoot()
                                                .get(orderBy)
                                )
                );
        return this;
    }

    public <U> EntitySelector<entityClass> addEqualityFilter(SingularAttribute<entityClass, U> field, U value) {
        predicates.add(
                criteriaHolder.getBuilder()
                        .equal(
                                criteriaHolder.getRoot()
                                        .get(field),
                                value
                        )
        );

        return this;
    }

    private EntitySelector<entityClass> readOnly() {
        this.isReadOnly = true;
        return this;
    }

    private Query<entityClass> createQuery() {
        var query = session.createQuery(criteriaHolder.getQuery())
                .setReadOnly(this.isReadOnly);

        Optional.ofNullable(this.graph)
                .ifPresent(query::applyLoadGraph);

        return query;
    }


    private void addPredicatesToQuery() {
        if (predicates.size() > 0) {
            criteriaHolder.getQuery()
                    .where(this.predicates.toArray(new Predicate[0]));
        }
    }

    private Query<entityClass> createQueryWithPredicates() {
        addPredicatesToQuery();
        return createQuery();
    }


    public entityClass get() {
        return createQueryWithPredicates()
                .getSingleResult();
    }

    public List<entityClass> getList() {
        return createQueryWithPredicates()
                .getResultList();
    }


}

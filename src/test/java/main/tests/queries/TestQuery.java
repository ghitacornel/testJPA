package main.tests.queries;

import entities.simple.Entity;
import entities.simple.Entity_;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TestQuery extends TransactionalSetup {

    private static List<Entity> buildModel() {
        List<Entity> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Entity entity = new Entity();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }
        return list;
    }

    @Before
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testQuery() {

        List<Entity> list = em.createQuery("select e from Entity e",
                Entity.class).getResultList();

        ReflectionAssert.assertReflectionEquals(buildModel(), list, ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test
    public void testWithNamedParameters() {

        List<Entity> list = em
                .createQuery("select e from Entity e where e.name = :name",
                        Entity.class).setParameter("name", "name 1")
                .getResultList();

        List<Entity> expected = new ArrayList<>();
        expected.add(buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(expected, list);
    }

    @Test
    public void testWithOrderParameters() {

        Entity entity = em
                .createQuery("select e from Entity e where e.name = ?1",
                        Entity.class).setParameter(1, "name 1")
                .getSingleResult();

        ReflectionAssert.assertReflectionEquals(buildModel().get(0), entity);
    }

    @Test
    public void testWithCriteriaBuilderMetamodel() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Entity> query = criteriaBuilder.createQuery(Entity.class);
        Root<Entity> from = query.from(Entity.class);
        query.where(criteriaBuilder.equal(from.get(Entity_.id), 2));
        TypedQuery<Entity> typedQuery = em.createQuery(query);

        Entity entity = typedQuery.getSingleResult();

        ReflectionAssert.assertReflectionEquals(buildModel().get(1), entity);
    }
}

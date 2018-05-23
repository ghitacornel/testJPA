package queries.simple;

import setup.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import queries.simple.SimpleQueryEntity;
import queries.simple.SimpleQueryEntity_;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSimpleQueries extends TransactionalSetup {

    private static List<SimpleQueryEntity> buildModel() {
        List<SimpleQueryEntity> list = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(6);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(5);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(3);
            entity.setName("name 3");
            entity.setValue(4);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(4);
            entity.setName("name 4");
            entity.setValue(3);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(5);
            entity.setName("name 5");
            entity.setValue(2);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(6);
            entity.setName("name 6");
            entity.setValue(null);
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
    public void testSelectAll() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e", SimpleQueryEntity.class).getResultList();
        ReflectionAssert.assertReflectionEquals(buildModel(), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectAllWithOrder() {

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e order by id desc", SimpleQueryEntity.class).getResultList();

        // verify, order is important
        List<SimpleQueryEntity> expected = buildModel();
        Collections.reverse(expected);
        ReflectionAssert.assertReflectionEquals(expected, actual);

    }

    @Test
    public void testSelectAllWithOrderAndBoundaries() {

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e order by id desc", SimpleQueryEntity.class).setFirstResult(1).setMaxResults(3).getResultList();

        // verify, order is important
        List<SimpleQueryEntity> expected = buildModel();
        Collections.reverse(expected);
        expected = expected.subList(1, 4);
        ReflectionAssert.assertReflectionEquals(expected, actual);

    }

    @Test
    public void testSelectAllWithNamedParameter() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.name = :name", SimpleQueryEntity.class).setParameter("name", "name 1").getResultList();

        List<SimpleQueryEntity> expected = new ArrayList<>();
        expected.add(buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(expected, list);

    }

    @Test
    public void testSelectAllWithOrderParameter() {

        SimpleQueryEntity entity = em.createQuery("select e from SQE e where e.name = ?1", SimpleQueryEntity.class).setParameter(1, "name 2").getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(1), entity);

    }

    @Test
    public void testFindByNameWithCriteriaBuilderNoMetaModel() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<SimpleQueryEntity> query = criteriaBuilder.createQuery(SimpleQueryEntity.class);
        Root<SimpleQueryEntity> from = query.from(SimpleQueryEntity.class);
        query.where(criteriaBuilder.equal(from.get("name"), "name 3"));
        TypedQuery<SimpleQueryEntity> typedQuery = em.createQuery(query);

        SimpleQueryEntity entity = typedQuery.getSingleResult();

        ReflectionAssert.assertReflectionEquals(buildModel().get(2), entity);
    }

    @Test
    public void testFindByNameWithCriteriaBuilderMetaModel() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<SimpleQueryEntity> query = criteriaBuilder.createQuery(SimpleQueryEntity.class);
        Root<SimpleQueryEntity> from = query.from(SimpleQueryEntity.class);
        query.where(criteriaBuilder.equal(from.get(SimpleQueryEntity_.name), "name 4"));
        TypedQuery<SimpleQueryEntity> typedQuery = em.createQuery(query);

        SimpleQueryEntity entity = typedQuery.getSingleResult();

        ReflectionAssert.assertReflectionEquals(buildModel().get(3), entity);
    }

    @Test
    public void testFindByIdNoQuery() {

        SimpleQueryEntity entity = em.find(SimpleQueryEntity.class, 5);
        ReflectionAssert.assertReflectionEquals(buildModel().get(4), entity);

    }

    @Test
    public void testWithNullParameter() {

        // TODO an equality check is performed against null => FAILURE
        List<SimpleQueryEntity> list1 = em.createQuery("select e from SQE e where e.value = :value", SimpleQueryEntity.class).setParameter("value", null).getResultList();
        ReflectionAssert.assertReflectionEquals(new ArrayList<>(), list1);


        // CORRECT way to check for nulls
        List<SimpleQueryEntity> list2 = em.createQuery("select e from SQE e where e.value IS NULL", SimpleQueryEntity.class).getResultList();
        List<SimpleQueryEntity> expected = new ArrayList<>();
        expected.add(buildModel().get(5));
        ReflectionAssert.assertReflectionEquals(expected, list2);

    }

    /**
     * TODO always check for null or empty collections passed as parameters since they cause generation of illegal SQL clauses such as empty IN<br>
     * TODO make sure the database IN SQL clause does not have a limit of allowed IN values
     */
    @Test
    public void testWithCollectionParameter() {

        List<String> parameter = new ArrayList<>();
        parameter.add("name 1");
        parameter.add("name 2");

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e where e.name IN :names", SimpleQueryEntity.class).setParameter("names", parameter).getResultList();
        ReflectionAssert.assertReflectionEquals(buildModel().subList(0, 2), actual);

    }
}

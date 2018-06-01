package queries.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

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
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(7);
            entity.setName("jOHn");
            entity.setValue(0);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(8);
            entity.setName("a john 1");
            entity.setValue(1);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(9);
            entity.setName("a second JOHN 2");
            entity.setValue(2);
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
    public void testWithNullClauseOK() {

        // CORRECT way to check for NULL
        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.value IS NULL", SimpleQueryEntity.class).getResultList();
        List<SimpleQueryEntity> expected = new ArrayList<>();
        expected.add(buildModel().get(5));
        ReflectionAssert.assertReflectionEquals(expected, list);

    }

    @Test
    public void testWithNullClauseFAIL() {

        // INCORRECT way to check for NULL
        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.value = :value", SimpleQueryEntity.class).setParameter("value", null).getResultList();
        Assert.assertTrue(list.isEmpty());

    }


    /**
     * TODO always check for null or empty collections passed as parameters
     * TODO passing null or empty collections as parameters can cause generation of corrupted SQLs having empty IN clauses
     * TODO some JPA implementations produce corrupted SQLs, or some databases accept suck SQLs and produce no results
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

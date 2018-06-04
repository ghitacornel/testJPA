package queries.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestMatchWithNULL extends TransactionalSetup {

    private static List<SimpleQueryEntity> buildModel() {
        List<SimpleQueryEntity> list = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(1);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
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
    public void testIsNotNull() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.value IS NOT NULL", SimpleQueryEntity.class).getResultList();
        List<SimpleQueryEntity> expected = new ArrayList<>();
        expected.add(buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(expected, list);

    }

    @Test
    public void testIsNull() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.value IS NULL", SimpleQueryEntity.class).getResultList();
        List<SimpleQueryEntity> expected = new ArrayList<>();
        expected.add(buildModel().get(1));
        ReflectionAssert.assertReflectionEquals(expected, list);

    }

    @Test
    public void testWrongCheckForNull() {

        // INCORRECT way to check for NULL
        // sometimes a simple check for null on server side can avoid an SQL query on database side
        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.value = :value", SimpleQueryEntity.class).setParameter("value", null).getResultList();
        Assert.assertTrue(list.isEmpty());

    }

    @Test
    public void testWrongCheckForNotNull() {

        // INCORRECT way to check for NOT NULL
        // sometimes a simple check for null on server side can avoid an SQL query on database side
        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.value <> :value", SimpleQueryEntity.class).setParameter("value", null).getResultList();
        Assert.assertTrue(list.isEmpty());

    }

}

package queries.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestFindByUsingLIKE extends TransactionalSetup {

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

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testWithEquals() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.name = :name", SimpleQueryEntity.class).setParameter("name", "jOHn").getResultList();
        Assertions.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(buildModel().get(2), list.get(0));

    }

    @Test
    public void testWithEqualsStringOnNumeric() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where cast(e.value as string) = :value", SimpleQueryEntity.class).setParameter("value", "6").getResultList();
        Assertions.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(buildModel().get(0), list.get(0));

    }

    /**
     * this is BAD, it is basically an equals matcher
     */
    @Test
    public void testWithLikeClauseBAD() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.name like :name", SimpleQueryEntity.class).setParameter("name", "jOHn").getResultList();
        Assertions.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(buildModel().get(2), list.get(0));

    }

    /**
     * this is still BAD, it is a LIKE matcher but case sensitive
     */
    @Test
    public void testWithLikeClauseStillBAD() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.name like :name", SimpleQueryEntity.class).setParameter("name", "%john%").getResultList();
        Assertions.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(buildModel().get(3), list.get(0));

    }

    /**
     * note the usage of "lower" on both sides
     * note the usage of %% for position match
     */
    @Test
    public void testWithLikeClauseStillBadDueToSpecialCharacterAddedOnInput() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where lower(e.name) like lower(:name)", SimpleQueryEntity.class).setParameter("name", "%john%").getResultList();
        Assertions.assertEquals(3, list.size());
        ReflectionAssert.assertReflectionEquals(buildModel().subList(2, 5), list);

    }

    /**
     * note the usage of "lower" on both sides
     * note the usage of %% for position match
     */
    @Test
    public void testWithLikeClauseOK() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where lower(e.name) like lower(concat('%',:name,'%'))", SimpleQueryEntity.class).setParameter("name", "john").getResultList();
        Assertions.assertEquals(3, list.size());
        ReflectionAssert.assertReflectionEquals(buildModel().subList(2, 5), list);

    }
}

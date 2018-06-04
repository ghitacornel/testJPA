package relationships.manytoone.strict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestCRUD extends TransactionalSetup {

    private MTOOStrictParent parentStrict1 = new MTOOStrictParent();
    private MTOOStrictParent parentStrict2 = new MTOOStrictParent();

    @Before
    public void before() {
        parentStrict1.setId(1);
        parentStrict1.setName("parent strict 1");
        persist(parentStrict1);

        parentStrict2.setId(2);
        parentStrict2.setName("parent strict 2");
        persist(parentStrict2);

        flushAndClear();
    }

    @Test
    public void testCRUD() {

        // assert empty
        Assert.assertTrue(em.createQuery("select t from MTOOStrictChild t").getResultList().isEmpty());
        flushAndClear();

        // insert
        MTOOStrictChild childStrict = new MTOOStrictChild();
        childStrict.setId(1);
        childStrict.setName("child name");
        childStrict.setParent(parentStrict1);
        em.persist(childStrict);
        flushAndClear();

        // test insert
        List<MTOOStrictChild> list = em.createQuery("select t from MTOOStrictChild t", MTOOStrictChild.class).getResultList();
        Assert.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(childStrict, list.get(0));
        flushAndClear();

        // update
        MTOOStrictChild existing = em.find(MTOOStrictChild.class, 1);
        existing.setName("new child name");// update name
        existing.setParent(parentStrict2);// update parent
        em.merge(existing);
        flushAndClear();

        // test update
        existing = em.find(MTOOStrictChild.class, 1);
        Assert.assertEquals("new child name", existing.getName());
        ReflectionAssert.assertReflectionEquals(parentStrict2, existing.getParent());
        flushAndClear();

        // remove
        existing = em.find(MTOOStrictChild.class, 1);
        Assert.assertNotNull(existing);
        em.remove(existing);
        flushAndClear();

        // test remove
        Assert.assertTrue(em.createQuery("select t from MTOOStrictChild t").getResultList().isEmpty());

        // this test verifies cascade is not present
        // cascade removal => test fails
        ReflectionAssert.assertReflectionEquals(parentStrict1, em.find(MTOOStrictParent.class, 1));
        ReflectionAssert.assertReflectionEquals(parentStrict2, em.find(MTOOStrictParent.class, 2));

    }
}

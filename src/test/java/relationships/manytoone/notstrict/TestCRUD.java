package relationships.manytoone.notstrict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestCRUD extends TransactionalSetup {

    private MTOONotStrictParent parentStrict1 = new MTOONotStrictParent();
    private MTOONotStrictParent parentStrict2 = new MTOONotStrictParent();

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
        Assert.assertTrue(em.createQuery("select t from MTOONotStrictChild t").getResultList().isEmpty());
        flushAndClear();

        // insert
        MTOONotStrictChild childStrict = new MTOONotStrictChild();
        childStrict.setId(1);
        childStrict.setName("child name");
        childStrict.setParent(parentStrict1);
        em.persist(childStrict);
        flushAndClear();

        // test insert
        List<MTOONotStrictChild> list = em.createQuery("select t from MTOONotStrictChild t", MTOONotStrictChild.class).getResultList();
        Assert.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(childStrict, list.get(0));
        flushAndClear();

        // update
        MTOONotStrictChild existing = em.find(MTOONotStrictChild.class, 1);
        existing.setName("new child name");// update name
        existing.setParent(parentStrict2);// update parent
        em.merge(existing);
        flushAndClear();

        // test update
        existing = em.find(MTOONotStrictChild.class, 1);
        Assert.assertEquals("new child name", existing.getName());
        ReflectionAssert.assertReflectionEquals(parentStrict2, existing.getParent());
        flushAndClear();

        // update and set parent to null
        existing = em.find(MTOONotStrictChild.class, 1);
        existing.setParent(null);
        flushAndClear();

        // test set parent to null
        existing = em.find(MTOONotStrictChild.class, 1);
        Assert.assertNull(existing.getParent());
        flushAndClear();

        // remove
        existing = em.find(MTOONotStrictChild.class, 1);
        Assert.assertNotNull(existing);
        em.remove(existing);
        flushAndClear();

        // test remove
        Assert.assertTrue(em.createQuery("select t from MTOONotStrictChild t").getResultList().isEmpty());

        // this test verifies cascade is not present
        // cascade removal => test fails
        ReflectionAssert.assertReflectionEquals(parentStrict1, em.find(MTOONotStrictParent.class, 1));
        ReflectionAssert.assertReflectionEquals(parentStrict2, em.find(MTOONotStrictParent.class, 2));

    }
}

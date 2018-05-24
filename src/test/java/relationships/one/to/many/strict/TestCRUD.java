package relationships.one.to.many.strict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestCRUD extends TransactionalSetup {

    private ParentStrict parentStrict1 = new ParentStrict();
    private ParentStrict parentStrict2 = new ParentStrict();

    @Before
    public void before() {
        parentStrict1.setId(1);
        parentStrict1.setName("parent strict");
        persist(parentStrict1);

        parentStrict2.setId(2);
        parentStrict2.setName("parent strict");
        persist(parentStrict2);

        flushAndClear();
    }

    @Test
    public void testCRUD() {

        // assert empty
        Assert.assertTrue(em.createQuery("select t from ChildStrict t").getResultList().isEmpty());
        flushAndClear();

        // insert
        ChildStrict childStrict = new ChildStrict();
        childStrict.setId(1);
        childStrict.setName("child name");
        childStrict.setParent(parentStrict1);
        em.persist(childStrict);
        flushAndClear();

        // test insert
        List<ChildStrict> list = em.createQuery("select t from ChildStrict t", ChildStrict.class).getResultList();
        Assert.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(childStrict, list.get(0));
        flushAndClear();

        // update
        ChildStrict existing = em.find(ChildStrict.class, 1);
        existing.setName("new child name");
        existing.setParent(parentStrict2);
        em.merge(existing);
        flushAndClear();

        // test update
        existing = em.find(ChildStrict.class, 1);
        Assert.assertEquals("new child name", existing.getName());
        ReflectionAssert.assertReflectionEquals(parentStrict2, existing.getParent());
        flushAndClear();

        // remove
        existing = em.find(ChildStrict.class, 1);
        Assert.assertNotNull(existing);
        em.remove(existing);
        flushAndClear();

        // test remove
        Assert.assertTrue(em.createQuery("select t from ChildStrict t").getResultList().isEmpty());

        // this test verifies cascade is not present
        // cascade removal => test fails
        ReflectionAssert.assertReflectionEquals(parentStrict1, em.find(ParentStrict.class, 1));
        ReflectionAssert.assertReflectionEquals(parentStrict2, em.find(ParentStrict.class, 2));

    }
}

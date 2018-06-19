package relationships.manytoone.strict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestCRUD extends TransactionalSetup {

    private MTOOStrictParent parent1 = new MTOOStrictParent();
    private MTOOStrictParent parent2 = new MTOOStrictParent();

    @Before
    public void before() {

        parent1.setId(1);
        parent1.setName("parent strict 1");
        persist(parent1);

        parent2.setId(2);
        parent2.setName("parent strict 2");
        persist(parent2);

        flushAndClear();

    }

    @Test
    public void testCRUD() {

        // verify no child is present
        verifyCorrespondingTableIsEmpty(MTOOStrictChild.class);

        // insert
        MTOOStrictChild child = new MTOOStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent1);
        em.persist(child);
        flushAndClear();

        // test insert
        List<MTOOStrictChild> list = em.createQuery("select t from MTOOStrictChild t", MTOOStrictChild.class).getResultList();
        Assert.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(child, list.get(0));
        flushAndClear();

        // update
        MTOOStrictChild existing = em.find(MTOOStrictChild.class, child.getId());
        existing.setName("new child name");// update name
        existing.setParent(parent2);// update parent
        em.merge(existing);
        flushAndClear();

        // test update
        existing = em.find(MTOOStrictChild.class, child.getId());
        Assert.assertEquals("new child name", existing.getName());
        ReflectionAssert.assertReflectionEquals(parent2, existing.getParent());
        flushAndClear();

        // remove
        existing = em.find(MTOOStrictChild.class, child.getId());
        Assert.assertNotNull(existing);
        em.remove(existing);
        flushAndClear();

        // test remove
        verifyCorrespondingTableIsEmpty(MTOOStrictChild.class);

        // verify parents are unaffected
        ReflectionAssert.assertReflectionEquals(parent1, em.find(MTOOStrictParent.class,parent1.getId()));
        ReflectionAssert.assertReflectionEquals(parent2, em.find(MTOOStrictParent.class,parent2.getId()));
    }

}

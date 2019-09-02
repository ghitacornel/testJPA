package relationships.manytoone.notstrict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestCRUD extends TransactionalSetup {

    private MTOONotStrictParent parent1 = new MTOONotStrictParent();
    private MTOONotStrictParent parent2 = new MTOONotStrictParent();

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
        verifyCorrespondingTableIsEmpty(MTOONotStrictChild.class);

        // insert
        MTOONotStrictChild child = new MTOONotStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent1);
        em.persist(child);
        flushAndClear();

        // test insert
        List<MTOONotStrictChild> list = em.createQuery("select t from MTOONotStrictChild t", MTOONotStrictChild.class).getResultList();
        Assert.assertEquals(1, list.size());
        ReflectionAssert.assertReflectionEquals(child, list.get(0));
        flushAndClear();

        // update
        MTOONotStrictChild existing = em.find(MTOONotStrictChild.class, child.getId());
        existing.setName("new child name");// update name
        existing.setParent(parent2);// update parent
        em.merge(existing);
        flushAndClear();

        // test update
        existing = em.find(MTOONotStrictChild.class, child.getId());
        Assert.assertEquals("new child name", existing.getName());
        ReflectionAssert.assertReflectionEquals(parent2, existing.getParent());
        flushAndClear();

        // remove
        em.remove(em.find(MTOONotStrictChild.class, child.getId()));
        flushAndClear();

        // test remove
        verifyCorrespondingTableIsEmpty(MTOONotStrictChild.class);

        // verify parents are unaffected
        ReflectionAssert.assertReflectionEquals(parent1, em.find(MTOONotStrictParent.class, parent1.getId()));
        ReflectionAssert.assertReflectionEquals(parent2, em.find(MTOONotStrictParent.class, parent2.getId()));

    }

}

package relationships.manytoone.notstrict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestSetParentToNull extends TransactionalSetup {

    private MTOONotStrictParent parent = new MTOONotStrictParent();

    @Before
    public void before() {

        verifyCorrespondingTableIsEmpty(MTOONotStrictChild.class);

        parent.setId(1);
        parent.setName("parent name");
        persist(parent);

        flushAndClear();
    }

    @Test
    public void testSetParentToNull() {

        // insert
        MTOONotStrictChild child = new MTOONotStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent);
        em.persist(child);
        flushAndClear();

        // update and set parent to null
        MTOONotStrictChild existing = em.find(MTOONotStrictChild.class, child.getId());
        existing.setParent(null);
        flushAndClear();

        // test set parent to null
        existing = em.find(MTOONotStrictChild.class, child.getId());
        Assert.assertNull(existing.getParent());// child has no parent
        ReflectionAssert.assertReflectionEquals(parent, em.find(MTOONotStrictParent.class, parent.getId()));// parent still exists

    }

}

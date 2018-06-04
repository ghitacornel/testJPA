package relationships.manytoone.strict;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestSetParentToNull extends TransactionalSetup {

    private MTOOStrictParent parent = new MTOOStrictParent();

    @Before
    public void before() {

        verifyCorrespondingTableIsEmpty(MTOOStrictChild.class);

        parent.setId(1);
        parent.setName("parent name");
        persist(parent);

        flushAndClear();

    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testSetParentToNull() {

        // insert
        MTOOStrictChild child = new MTOOStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent);
        em.persist(child);
        flushAndClear();

        // update and set parent to null
        MTOOStrictChild existing = em.find(MTOOStrictChild.class, child.getId());
        existing.setParent(null);
        flushAndClear();

    }
}

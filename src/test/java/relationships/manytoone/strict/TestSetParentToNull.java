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

    /**
     * setting the parent to null makes the test throw an exception<br>
     * for strict relationships children are totally dependent of their parents
     */
    @Test(expected = javax.persistence.PersistenceException.class)
    public void testSetParentToNull() {

        // insert
        MTOOStrictChild child = new MTOOStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent);
        em.persist(child);
        flushAndClear();

        // set parent to null
        em.find(MTOOStrictChild.class, child.getId()).setParent(null);
        flushAndClear();

    }
}

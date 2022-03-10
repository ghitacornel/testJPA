package relationships.manytoone.strict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;

public class TestSetParentToNull extends TransactionalSetup {

    private final MTOOStrictParent parent = new MTOOStrictParent();

    @BeforeEach
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
    @Test
    public void testSetParentToNull() {

        // insert
        MTOOStrictChild child = new MTOOStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent);
        em.persist(child);
        flushAndClear();

        // set parent to null
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.find(MTOOStrictChild.class, child.getId()).setParent(null);
            flushAndClear();
        });
    }
}

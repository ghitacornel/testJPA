package relationships.manytoone.notstrict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestSetParentToNull extends TransactionalSetup {

    private MTOONotStrictParent parent = new MTOONotStrictParent();

    @BeforeEach
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

        //  set parent to null
        em.find(MTOONotStrictChild.class, child.getId()).setParent(null);
        flushAndClear();

        // test set parent to null
        Assertions.assertNull(em.find(MTOONotStrictChild.class, child.getId()).getParent());

        // verify parent is unaffected
        ReflectionAssert.assertReflectionEquals(parent, em.find(MTOONotStrictParent.class,parent.getId()));

    }

}

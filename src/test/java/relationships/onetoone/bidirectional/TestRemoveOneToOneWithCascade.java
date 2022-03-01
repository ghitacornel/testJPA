package relationships.onetoone.bidirectional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestRemoveOneToOneWithCascade extends TransactionalSetup {

    private final A parent = buildModel();

    private A buildModel() {

        A a = new A();
        a.setId(1);
        a.setName("this is a");

        B b = new B();
        b.setId(2);
        b.setName("this is b");
        b.setA(a);
        a.setB(b);

        return a;
    }

    @BeforeEach
    public void before() {
        em.persist(parent);
        flushAndClear();
    }

    @Test
    public void testRemovalOfParentTriggersTheRemovalOfOrphanChild() {

        Assertions.assertNotNull(em.find(A.class, 1));
        Assertions.assertNotNull(em.find(B.class, 2));

        em.remove(em.find(A.class, 1));
        flushAndClear();

        verifyCorrespondingTableIsEmpty(A.class);
        verifyCorrespondingTableIsEmpty(B.class);

    }

    @Test
    public void testChildIsRemovedWhenItsParentIsSetToNull() {

        Assertions.assertNotNull(em.find(A.class, 1));
        Assertions.assertNotNull(em.find(B.class, 2));

        em.find(A.class, 1).setB(null);
        flushAndClear();

        Assertions.assertNotNull(em.find(A.class, 1));
        Assertions.assertNull(em.find(B.class, 2));

    }

    @Test
    public void testDirectRemovalOfChildWorksWhenParentIsNotLoaded() {

        Assertions.assertNotNull(em.find(B.class, 2));

        // works since the parent was not loaded
        em.remove(em.find(B.class, 2));
        flushAndClear();

        Assertions.assertNotNull(em.find(A.class, 1));
        Assertions.assertNull(em.find(B.class, 2));

    }

    @Test
    public void testDirectRemovalOfChildDoesNotWorkWhenParentWasLoaded() {

        Assertions.assertNotNull(em.find(A.class, 1));// loads the parent
        Assertions.assertNotNull(em.find(B.class, 2));

        // it fails due to the previously loaded parent and relationship not removed
        em.remove(em.find(B.class, 2));
        flushAndClear();

        Assertions.assertNotNull(em.find(A.class, 1));
        Assertions.assertNotNull(em.find(B.class, 2));

    }

    @Test
    public void testDirectRemovalOfChildDoesWorksWhenParentWasLoadedAndRelationshipUpdated() {

        Assertions.assertNotNull(em.find(A.class, 1));// loads the parent
        Assertions.assertNotNull(em.find(B.class, 2));

        em.find(A.class, 1).setB(null);// update relationship
        em.remove(em.find(B.class, 2));
        flushAndClear();

        Assertions.assertNotNull(em.find(A.class, 1));
        Assertions.assertNull(em.find(B.class, 2));

    }

}

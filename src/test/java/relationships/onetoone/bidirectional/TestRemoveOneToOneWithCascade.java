package relationships.onetoone.bidirectional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void before() {
        em.persist(parent);
        flushAndClear();
    }

    @Test
    public void testRemovalOfParentTriggersTheRemovalOfOrphanChild() {

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNotNull(em.find(B.class, 2));

        em.remove(em.find(A.class, 1));
        flushAndClear();

        verifyCorrespondingTableIsEmpty(A.class);
        verifyCorrespondingTableIsEmpty(B.class);

    }

    @Test
    public void testChildIsRemovedWhenItsParentIsSetToNull() {

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNotNull(em.find(B.class, 2));

        em.find(A.class, 1).setB(null);
        flushAndClear();

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNull(em.find(B.class, 2));

    }

    @Test
    public void testDirectRemovalOfChildWorksWhenParentIsNotLoaded() {

        Assert.assertNotNull(em.find(B.class, 2));

        // works since the parent was not loaded
        em.remove(em.find(B.class, 2));
        flushAndClear();

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNull(em.find(B.class, 2));

    }

    @Test
    public void testDirectRemovalOfChildDoesNotWorkWhenParentWasLoaded() {

        Assert.assertNotNull(em.find(A.class, 1));// loads the parent
        Assert.assertNotNull(em.find(B.class, 2));

        // it fails due to the previously loaded parent and relationship not removed
        em.remove(em.find(B.class, 2));
        flushAndClear();

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNotNull(em.find(B.class, 2));

    }

    @Test
    public void testDirectRemovalOfChildDoesWorksWhenParentWasLoadedAndRelationshipUpdated() {

        Assert.assertNotNull(em.find(A.class, 1));// loads the parent
        Assert.assertNotNull(em.find(B.class, 2));

        em.find(A.class, 1).setB(null);// update relationship
        em.remove(em.find(B.class, 2));
        flushAndClear();

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNull(em.find(B.class, 2));

    }

}

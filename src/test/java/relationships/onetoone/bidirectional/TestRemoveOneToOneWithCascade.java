package relationships.onetoone.bidirectional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestRemoveOneToOneWithCascade extends TransactionalSetup {

    private A model = buildModel();

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
        em.persist(model);
        flushAndClear();
    }

    @Test
    public void testRemoveParentTriggersTheRemovalOfOrphanChild() {

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNotNull(em.find(B.class, 2));

        em.remove(em.find(A.class, 1));
        flushAndClear();

        Assert.assertNull(em.find(A.class, 1));
        Assert.assertNull(em.find(B.class, 2));

    }

    @Test
    public void testRemoveChildOK1() {

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNotNull(em.find(B.class, 2));

        em.find(A.class, 1).setB(null);
        flushAndClear();

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNull(em.find(B.class, 2));

    }

    @Test
    public void testRemoveChildOK2() {

        Assert.assertNotNull(em.find(B.class, 2));

        // works since the parent was not loaded
        em.remove(em.find(B.class, 2));
        flushAndClear();

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNull(em.find(B.class, 2));

    }

    @Test
    public void testRemoveChildFAIL1() {

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNotNull(em.find(B.class, 2));

        // it fails due to the previously loaded parent and cascaded all marker on relationship
        em.remove(em.find(B.class, 2));
        flushAndClear();

        Assert.assertNotNull(em.find(A.class, 1));
        Assert.assertNotNull(em.find(B.class, 2));

    }

}

package relationships.orphans.onetoone.notowningside;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOOrphanNotOwningSideNotOwner a;
    OTOOrphanNotOwningSideOwner b;

    @Before
    public void setUp() {

        verifyCorrespondingTableIsEmpty(OTOOrphanNotOwningSideNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanNotOwningSideOwner.class);

        a = new OTOOrphanNotOwningSideNotOwner();
        a.setId(1);
        a.setName("a");

        b = new OTOOrphanNotOwningSideOwner();
        b.setId(1);
        b.setName("b");

        a.setB(b);
        b.setA(a);

        persist(a, b);
        flushAndClear();

    }

    @Test
    public void testRemoveOrphanB() {

        // mark B as orphan
        em.find(OTOOrphanNotOwningSideOwner.class, b.getId()).setA(null);
        flushAndClear();

        // since we remove the part not flagged as orphanRemoval only the link is removed

        {// adjust model to match expectations
            b.setA(null);
        }
        ReflectionAssert.assertReflectionEquals(b, em.find(OTOOrphanNotOwningSideOwner.class, b.getId()));
        {// adjust model to match expectations
            a.setB(null);
        }
        ReflectionAssert.assertReflectionEquals(a, em.find(OTOOrphanNotOwningSideNotOwner.class, a.getId()));

    }

    @Test
    public void testRemoveOrphanA() {

        // mark A as orphan
        em.find(OTOOrphanNotOwningSideNotOwner.class, a.getId()).setB(null);
        flushAndClear();

        // since we remove the part flagged as orphanRemoval the other side is removed regardless that it is the owning side

        Assert.assertNull(em.find(OTOOrphanNotOwningSideOwner.class, b.getId()));
        {// adjust model to match expectations
            a.setB(null);
        }
        ReflectionAssert.assertReflectionEquals(a, em.find(OTOOrphanNotOwningSideNotOwner.class, a.getId()));

    }
}

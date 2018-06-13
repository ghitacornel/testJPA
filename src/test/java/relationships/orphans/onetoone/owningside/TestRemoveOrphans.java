package relationships.orphans.onetoone.owningside;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOOrphanOwningSideA a;
    OTOOrphanOwningSideB b;

    @Before
    public void setUp() {

        verifyCorrespondingTableIsEmpty(OTOOrphanOwningSideA.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanOwningSideB.class);

        a = new OTOOrphanOwningSideA();
        a.setId(1);
        a.setName("a");

        b = new OTOOrphanOwningSideB();
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
        em.find(OTOOrphanOwningSideB.class, b.getId()).setA(null);
        flushAndClear();

        Assert.assertNull(em.find(OTOOrphanOwningSideA.class, a.getId()));
        {// adjust model to match expectations
            b.setA(null);
        }
        ReflectionAssert.assertReflectionEquals(b, em.find(OTOOrphanOwningSideB.class, b.getId()));

    }

    @Test
    public void testRemoveOrphanA() {

        // mark A as orphan
        em.find(OTOOrphanOwningSideA.class, a.getId()).setB(null);
        flushAndClear();

        // observe nothing happened since A is not the owning side
        ReflectionAssert.assertReflectionEquals(a, em.find(OTOOrphanOwningSideA.class, a.getId()));
        ReflectionAssert.assertReflectionEquals(b, em.find(OTOOrphanOwningSideB.class, b.getId()));

    }
}

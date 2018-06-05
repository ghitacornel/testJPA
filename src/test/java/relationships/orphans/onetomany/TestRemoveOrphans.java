package relationships.orphans.onetomany;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOMOrphanA a;
    OTOMOrphanB b1;
    OTOMOrphanB b2;

    @Before
    public void setUp() {

        a = new OTOMOrphanA();
        a.setId(1);
        a.setName("a");

        b1 = new OTOMOrphanB();
        b1.setId(1);
        b1.setName("b1");
        b1.setA(a);

        b2 = new OTOMOrphanB();
        b2.setId(2);
        b2.setName("b2");
        b2.setA(a);

        a.getBs().add(b1);
        a.getBs().add(b2);
        persist(a, b1, b2);

        flushAndClear();

    }

    @Test
    public void testRemoveOrphansWhenParentIsRemoved() {

        OTOMOrphanA existingA = em.find(OTOMOrphanA.class, a.getId());
        em.remove(existingA);
        flushAndClear();

        // test orphans are removed
        Assert.assertNull(em.find(OTOMOrphanB.class, b1.getId()));
        Assert.assertNull(em.find(OTOMOrphanB.class, b2.getId()));

    }

    @Test
    public void testRemoveOrphanBySettingParentToNullDoesNotWork() {

        em.find(OTOMOrphanB.class, b2.getId()).setA(null);
        flushAndClear();

        // test orphan is not removed
        {// adjust model to match expectations
            b2.setA(null);
        }
        ReflectionAssert.assertReflectionEquals(b2, em.find(OTOMOrphanB.class, b2.getId()));

    }

    @Test
    public void testRemoveOrphanByRemovingFromParentListOnlyDoesNotWork() {

        em.find(OTOMOrphanA.class, a.getId()).getBs().remove(1);
        flushAndClear();

        // test orphan is not removed
        ReflectionAssert.assertReflectionEquals(b1, em.find(OTOMOrphanB.class, b1.getId()));
        ReflectionAssert.assertReflectionEquals(b2, em.find(OTOMOrphanB.class, b2.getId()));
        ReflectionAssert.assertReflectionEquals(a, em.find(OTOMOrphanA.class, a.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    // TODO verify with other JPA implementations / versions
    @Test
    public void testRemoveOrphanByRemovingFromParentListAndSetParentToNullDoesNotWork() {

        em.find(OTOMOrphanA.class, a.getId()).getBs().remove(1);
        em.find(OTOMOrphanB.class, b2.getId()).setA(null);
        flushAndClear();

        // test orphan is not removed
        {// adjust model to match expectations
            b2.setA(null);
        }
        ReflectionAssert.assertReflectionEquals(b2, em.find(OTOMOrphanB.class, b2.getId()));
        {// adjust model to match expectations
            a.getBs().remove(1);
        }
        ReflectionAssert.assertReflectionEquals(a, em.find(OTOMOrphanA.class, a.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}

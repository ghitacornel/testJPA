package relationships.orphans.onetomany;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOMOrphanA parent;
    OTOMOrphanB child1;
    OTOMOrphanB child2;

    @Before
    public void setUp() {

        verifyCorrespondingTableIsEmpty(OTOMOrphanA.class);
        verifyCorrespondingTableIsEmpty(OTOMOrphanB.class);

        parent = new OTOMOrphanA();
        parent.setId(1);
        parent.setName("a");

        child1 = new OTOMOrphanB();
        child1.setId(1);
        child1.setName("child1");
        child1.setA(parent);

        child2 = new OTOMOrphanB();
        child2.setId(2);
        child2.setName("child2");
        child2.setA(parent);

        parent.getBs().add(child1);
        parent.getBs().add(child2);
        persist(parent, child1, child2);

        flushAndClear();

    }

    @Test
    public void testRemoveOrphansWhenParentIsRemoved() {

        // remove parent
        OTOMOrphanA parent = em.find(OTOMOrphanA.class, this.parent.getId());
        em.remove(parent);
        flushAndClear();

        // test parent was removed
        verifyCorrespondingTableIsEmpty(OTOMOrphanA.class);

        // test orphans are removed
        verifyCorrespondingTableIsEmpty(OTOMOrphanB.class);

    }

    @Test
    public void testRemoveOrphanBySettingParentToNullDoesNotWork() {

        em.find(OTOMOrphanB.class, child2.getId()).setA(null);
        flushAndClear();

        // test orphan is not removed
        {// adjust model to match expectations
            child2.setA(null);
        }
        ReflectionAssert.assertReflectionEquals(child2, em.find(OTOMOrphanB.class, child2.getId()));

    }

    @Test
    public void testRemoveOrphanByRemovingFromParentListOnlyDoesNotWork() {

        em.find(OTOMOrphanA.class, parent.getId()).getBs().remove(1);
        flushAndClear();

        // test orphan is not removed
        ReflectionAssert.assertReflectionEquals(child1, em.find(OTOMOrphanB.class, child1.getId()));
        ReflectionAssert.assertReflectionEquals(child2, em.find(OTOMOrphanB.class, child2.getId()));
        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMOrphanA.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    // TODO verify with other JPA implementations / versions
    @Test
    public void testRemoveOrphanByRemovingFromParentListAndSetParentToNullDoesNotWork() {

        OTOMOrphanB toRemoveChild = em.find(OTOMOrphanA.class, parent.getId()).getBs().remove(1);
        toRemoveChild.setA(null);
        flushAndClear();

        // test orphan is not removed
        {// adjust model to match expectations
            child2.setA(null);
        }
        ReflectionAssert.assertReflectionEquals(child2, em.find(OTOMOrphanB.class, child2.getId()));
        {// adjust model to match expectations
            parent.getBs().remove(1);
        }
        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMOrphanA.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}

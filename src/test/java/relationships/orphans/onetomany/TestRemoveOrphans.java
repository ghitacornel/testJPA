package relationships.orphans.onetomany;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOMOrphanParent parent;
    OTOMOrphanChild child1;
    OTOMOrphanChild child2;

    @BeforeEach
    public void setUp() {

        verifyCorrespondingTableIsEmpty(OTOMOrphanParent.class);
        verifyCorrespondingTableIsEmpty(OTOMOrphanChild.class);

        parent = new OTOMOrphanParent();
        parent.setId(1);
        parent.setName("a");

        child1 = new OTOMOrphanChild();
        child1.setId(1);
        child1.setName("child1");
        child1.setParent(parent);

        child2 = new OTOMOrphanChild();
        child2.setId(2);
        child2.setName("child2");
        child2.setParent(parent);

        parent.getChildren().add(child1);
        parent.getChildren().add(child2);
        persist(parent, child1, child2);

        flushAndClear();

    }

    @Test
    public void testOrphansAreRemovedWhenParentIsRemoved() {

        // remove parent
        OTOMOrphanParent parent = em.find(OTOMOrphanParent.class, this.parent.getId());
        em.remove(parent);
        flushAndClear();

        // test parent was removed
        verifyCorrespondingTableIsEmpty(OTOMOrphanParent.class);

        // test orphans are removed
        verifyCorrespondingTableIsEmpty(OTOMOrphanChild.class);

    }

    /**
     * In this case Orphan is not removed since it is a NOT STRICT mapping
     */
    @Test
    public void testSettingParentToNullRemovesOnlyTheRelationshipAndNotTheOrphanedChild() {

        em.find(OTOMOrphanChild.class, child2.getId()).setParent(null);
        flushAndClear();

        // test orphan is not removed
        {// adjust model to match expectations
            child2.setParent(null);
        }
        ReflectionAssert.assertReflectionEquals(child2, em.find(OTOMOrphanChild.class, child2.getId()));

    }

    /**
     * In this case Orphan is not removed since it is a NOT STRICT mapping
     */
    @Test
    public void testRemoveOrphanFromParentListRemovesOnlyTheRelationshipAndNotTheOrphanedChild() {

        em.find(OTOMOrphanParent.class, parent.getId()).getChildren().remove(1);
        flushAndClear();

        // test orphan is not removed
        ReflectionAssert.assertReflectionEquals(child1, em.find(OTOMOrphanChild.class, child1.getId()));
        ReflectionAssert.assertReflectionEquals(child2, em.find(OTOMOrphanChild.class, child2.getId()));
        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMOrphanParent.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    /**
     * In this case Orphan is not removed since it is a NOT STRICT mapping
     */
    @Test
    public void testRemoveOrphanByRemovingFromParentListAndSetParentToNullRemovesOnlyTheRelationshipAndNotTheOrphanedChild() {

        OTOMOrphanChild toRemoveChild = em.find(OTOMOrphanParent.class, parent.getId()).getChildren().remove(1);
        toRemoveChild.setParent(null);
        flushAndClear();

        // test orphan is not removed
        {// adjust model to match expectations
            child2.setParent(null);
        }
        ReflectionAssert.assertReflectionEquals(child2, em.find(OTOMOrphanChild.class, child2.getId()));
        {// adjust model to match expectations
            parent.getChildren().remove(1);
        }
        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMOrphanParent.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}

package relationships.onetomany.notstrict;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestOrphanChild extends TransactionalSetup {

    private OTOMNotStrictParent parent = buildModel();

    private OTOMNotStrictParent buildModel() {

        OTOMNotStrictParent parent = new OTOMNotStrictParent();
        parent.setId(1);
        parent.setName("parent name");

        for (int i = 1; i <= 3; i++) {
            OTOMNotStrictChild child = new OTOMNotStrictChild();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        em.persist(parent);
        persist(parent.getChildren());
        flushAndClear();
    }

    @Test
    public void testMakeOrphanChildBySettingParentToNull() {

        OTOMNotStrictChild toRemoveChild = em.find(OTOMNotStrictParent.class, parent.getId()).getChildren().get(1);
        toRemoveChild.setParent(null);// mark child as orphan
        flushAndClear();

        // test parent and other children are unaffected
        OTOMNotStrictParent expectedParent = buildModel();
        expectedParent.getChildren().remove(1);
        ReflectionAssert.assertReflectionEquals(expectedParent, em.find(OTOMNotStrictParent.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

        // test child is now orphan
        OTOMNotStrictChild child = em.find(OTOMNotStrictChild.class, toRemoveChild.getId());
        ReflectionAssert.assertReflectionEquals(toRemoveChild, child);

    }

    @Test
    public void testMakeOrphanChildByRemovingFromItsParentChildrenListDoesNotWork() {

        em.find(OTOMNotStrictParent.class, parent.getId()).getChildren().remove(1);// remove child from its parent children list
        flushAndClear();

        // test nothing happened
        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMNotStrictParent.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);


    }

}

package relationships.onetomany.strict;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import relationships.onetomany.oneside.OTOMOneSideParent;
import relationships.onetomany.oneside.OTOMSOneSideChild;
import setup.TransactionalSetup;

public class TestRemoveParent extends TransactionalSetup {

    private final OTOMStrictParent parent = buildModel();

    private OTOMStrictParent buildModel() {

        OTOMStrictParent parent = new OTOMStrictParent();
        parent.setId(1);
        parent.setName("parent name");

        for (int i = 1; i <= 3; i++) {
            OTOMStrictChild child = new OTOMStrictChild();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(OTOMStrictParent.class);
        verifyCorrespondingTableIsEmpty(OTOMStrictChild.class);
        em.persist(parent);
        flushAndClear();
    }

    @Test
    public void testRemoveParentRemovesAllOrphanedChildren() {

        // remove parent
        em.remove(em.find(OTOMStrictParent.class, parent.getId()));
        flushAndClear();

        // verify parent and children are removed
        verifyCorrespondingTableIsEmpty(OTOMStrictParent.class);
        verifyCorrespondingTableIsEmpty(OTOMStrictChild.class);

    }

    @Test
    public void testClearParentChildrenListRemovesAllOrphanedChildren() {

        // clear parent children list
        em.find(OTOMStrictParent.class, parent.getId()).getChildren().clear();
        flushAndClear();

        // verify children are removed
        verifyCorrespondingTableIsEmpty(OTOMStrictChild.class);

        parent.getChildren().clear();
        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMStrictParent.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

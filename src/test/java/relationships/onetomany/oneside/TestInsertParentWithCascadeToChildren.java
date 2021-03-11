package relationships.onetomany.oneside;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestInsertParentWithCascadeToChildren extends TransactionalSetup {

    private OTOMOneSideParent parent = buildModel();

    private OTOMOneSideParent buildModel() {

        OTOMOneSideParent parent = new OTOMOneSideParent();
        parent.setId(1);
        parent.setName("parent name");

        for (int i = 1; i <= 3; i++) {
            OTOMSOneSideChild child = new OTOMSOneSideChild();
            child.setId(i);
            child.setName("child " + i);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(OTOMOneSideParent.class);
        verifyCorrespondingTableIsEmpty(OTOMSOneSideChild.class);
    }

    @Test
    public void test() {

        em.persist(parent);
        flushAndClear();

        OTOMOneSideParent existing = em.find(OTOMOneSideParent.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(parent, existing, ReflectionComparatorMode.LENIENT_ORDER);

    }
}

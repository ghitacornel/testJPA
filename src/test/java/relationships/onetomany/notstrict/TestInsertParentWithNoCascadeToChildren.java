package relationships.onetomany.notstrict;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestInsertParentWithNoCascadeToChildren extends TransactionalSetup {

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
        verifyCorrespondingTableIsEmpty(OTOMNotStrictParent.class);
        verifyCorrespondingTableIsEmpty(OTOMNotStrictChild.class);
    }

    @Test
    public void test() {

        // persist only the parent
        em.persist(parent);
        flushAndClear();

        // verify children are not persisted
        {// adjust model to reflect expectations
            parent.getChildren().clear();
        }
        OTOMNotStrictParent existing = em.find(OTOMNotStrictParent.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(parent, existing, ReflectionComparatorMode.LENIENT_ORDER);

    }
}

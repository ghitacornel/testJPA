package relationships.onetomany.strict;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestInsertParentWithCascadeToChildren extends TransactionalSetup {

    private OTOMStrictParent parent = buildModel();

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
    }

    @Test
    public void test() {

        em.persist(parent);
        flushAndClear();

        OTOMStrictParent existing = em.find(OTOMStrictParent.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(parent, existing, ReflectionComparatorMode.LENIENT_ORDER);

    }
}

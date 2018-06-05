package relationships.onetomany.strict;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestUpdateParentAndChildren extends TransactionalSetup {

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
        em.persist(parent);
        flushAndClear();
    }

    @Test
    public void test() {

        OTOMStrictParent existing1 = em.find(OTOMStrictParent.class, parent.getId());

        {
            existing1.setName("new name");

            OTOMStrictChild child = new OTOMStrictChild();
            child.setId(4);
            child.setName("child 4");
            child.setParent(existing1);
            existing1.getChildren().add(child);

            existing1.getChildren().get(0).setName("new name 1");
        }

        em.merge(existing1);
        flushAndClear();

        OTOMStrictParent existing2 = em.find(OTOMStrictParent.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(existing1, existing2, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(existing1.getChildren(), existing2.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

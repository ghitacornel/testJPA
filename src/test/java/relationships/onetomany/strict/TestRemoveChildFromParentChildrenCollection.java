package relationships.onetomany.strict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestRemoveChildFromParentChildrenCollection extends TransactionalSetup {

    private OTOMStrictParent model = buildModel();

    private OTOMStrictParent buildModel() {

        OTOMStrictParent parent = new OTOMStrictParent();
        parent.setId(1);
        parent.setName("parent 1");
        parent.setChildren(new ArrayList<>());

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
        em.persist(model);
        flushAndClear();
    }

    @Test
    public void testRemoveDirectlyFromCollection() {

        OTOMStrictParent existing1 = em.find(OTOMStrictParent.class, model.getId());
        existing1.getChildren().remove(1);
        flushAndClear();

        OTOMStrictParent existing2 = em.find(OTOMStrictParent.class, model.getId());
        OTOMStrictParent expectedParent = buildModel();
        expectedParent.getChildren().remove(1);
        ReflectionAssert.assertReflectionEquals(expectedParent.getChildren(), existing2.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

        // child is removed since it is now an orphan
        OTOMStrictChild child = em.find(OTOMStrictChild.class, buildModel().getChildren().get(1).getId());
        Assert.assertNull(child);

    }

    @Test
    public void testRemoveFromEMAndThenFromCollection() {

        OTOMStrictParent existing1 = em.find(OTOMStrictParent.class, model.getId());
        em.remove(existing1.getChildren().get(1));
        existing1.getChildren().remove(1);//without this it won't work
        flushAndClear();

        OTOMStrictParent existing2 = em.find(OTOMStrictParent.class, model.getId());
        OTOMStrictParent expectedParent = buildModel();
        expectedParent.getChildren().remove(1);
        ReflectionAssert.assertReflectionEquals(expectedParent.getChildren(), existing2.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

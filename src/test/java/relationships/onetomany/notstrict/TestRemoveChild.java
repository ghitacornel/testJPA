package relationships.onetomany.notstrict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestRemoveChild extends TransactionalSetup {

    private OTOMNotStrictParent parent = buildModel();

    private OTOMNotStrictParent buildModel() {

        OTOMNotStrictParent parent = new OTOMNotStrictParent();
        parent.setId(1);
        parent.setName("parent name");
        parent.setChildren(new ArrayList<>());

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
    public void testRemoveChild() {

        OTOMNotStrictChild toRemoveChild = em.find(OTOMNotStrictParent.class, parent.getId()).getChildren().get(1);
        em.remove(toRemoveChild);
        flushAndClear();

        // test parent and other children are unaffected
        OTOMNotStrictParent existingParent = em.find(OTOMNotStrictParent.class, parent.getId());
        OTOMNotStrictParent expectedParent = buildModel();
        expectedParent.getChildren().remove(1);
        ReflectionAssert.assertReflectionEquals(expectedParent, existingParent, ReflectionComparatorMode.LENIENT_ORDER);

        // test child is now removed
        Assert.assertNull(em.find(OTOMNotStrictChild.class, toRemoveChild.getId()));

    }

}

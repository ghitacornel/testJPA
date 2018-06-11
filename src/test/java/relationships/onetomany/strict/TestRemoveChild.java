package relationships.onetomany.strict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemoveChild extends TransactionalSetup {

    private OTOMStrictParent model = buildModel();

    private OTOMStrictParent buildModel() {

        OTOMStrictParent parent = new OTOMStrictParent();
        parent.setId(1);
        parent.setName("parent 1");

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
    public void testRemoveChildByRemovingDirectlyFromItsParentChildrenList() {

        em.find(OTOMStrictParent.class, model.getId()).getChildren().remove(1);
        flushAndClear();

        OTOMStrictParent existingParent = em.find(OTOMStrictParent.class, model.getId());
        OTOMStrictParent expectedParent = buildModel();
        {// adjust model to reflect expected changes
            expectedParent.getChildren().remove(1);
        }
        ReflectionAssert.assertReflectionEquals(expectedParent, existingParent, ReflectionComparatorMode.LENIENT_ORDER);

        // child is removed since it is now an orphan
        OTOMStrictChild child = em.find(OTOMStrictChild.class, 1);
        Assert.assertNull(child);

    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testRemoveChildByMakingItOrphanDoesNotWorkDueToConstraint() {

        em.find(OTOMStrictParent.class, model.getId()).getChildren().get(1).setParent(null);
        flushAndClear();

    }
}

package relationships.onetomany.notstrict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestRemoveParent extends TransactionalSetup {

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
        verifyCorrespondingTableIsEmpty(OTOMNotStrictParent.class);
        verifyCorrespondingTableIsEmpty(OTOMNotStrictChild.class);
        em.persist(parent);
        persist(parent.getChildren());
        flushAndClear();
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testRemoveParentFailsDueToExistingChildren() {

        // remove parent
        em.remove(em.find(OTOMNotStrictParent.class, parent.getId()));
        flushAndClear();

    }

    @Test
    public void testRemoveParentRequiresMarkingItsChildrenAsOrphans() {

        OTOMNotStrictParent existing = em.find(OTOMNotStrictParent.class, parent.getId());

        // first make children orphan
        for (OTOMNotStrictChild child : existing.getChildren()) {
            child.setParent(null);
        }

        // second remove the parent
        em.remove(em.find(OTOMNotStrictParent.class, parent.getId()));
        flushAndClear();

        // verify parent is removed
        Assert.assertNull(em.find(OTOMNotStrictParent.class, this.parent.getId()));

        // verify parent children are orphaned
        {// adjust model to match expectations
            for (OTOMNotStrictChild child : parent.getChildren()) {
                child.setParent(null);
            }
        }
        ReflectionAssert.assertReflectionEquals(parent.getChildren(), em.createQuery("select t from OTOMNotStrictChild t", OTOMNotStrictChild.class).getResultList(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

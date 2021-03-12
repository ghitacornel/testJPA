package relationships.onetomany.oneside;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.Collections;

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
    public void testInsertParentWithCascadeToChildren() {

        em.persist(parent);
        flushAndClear();

        OTOMOneSideParent existing = em.find(OTOMOneSideParent.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(parent, existing, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testInsertChildDoesNotWork() {
        OTOMSOneSideChild child = new OTOMSOneSideChild();
        child.setId(1);
        child.setName("child ");
        em.persist(child);
        flushAndClear();
    }

    @Test
    public void testAddChildToParent() {

        em.persist(parent);
        flushAndClear();

        OTOMSOneSideChild child = new OTOMSOneSideChild();
        child.setId(11);
        child.setName("child 11");
        em.find(OTOMOneSideParent.class, parent.getId()).getChildren().add(child);
        flushAndClear();

        // adjust
        parent.getChildren().add(child);
        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOMOneSideParent.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testResetChildrenListDoesNotRemoveOldChildren() {

        em.persist(parent);
        flushAndClear();

        OTOMSOneSideChild child = new OTOMSOneSideChild();
        child.setId(11);
        child.setName("child 11");
        em.find(OTOMOneSideParent.class, parent.getId()).setChildren(Collections.singletonList(child));
        flushAndClear();

    }
}

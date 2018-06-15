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

        for (int i = 1; i <= 2; i++) {
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
    public void testUpdateLoadedParentAndChildren() {

        OTOMStrictParent existing1 = em.find(OTOMStrictParent.class, parent.getId());

        existing1.setName("new name");

        {// remove child with id 1
            OTOMStrictChild toRemove = null;
            for (OTOMStrictChild child : existing1.getChildren()) {
                if (child.getId() == 1) {
                    toRemove = child;
                }
            }
            existing1.getChildren().remove(toRemove);
        }

        {// update the single one left
            existing1.getChildren().get(0).setName("new name 2");
        }

        {// add new child
            OTOMStrictChild child = new OTOMStrictChild();
            child.setId(3);
            child.setName("child 3");
            child.setParent(existing1);
            existing1.getChildren().add(child);
        }

        flushAndClear();

        {// adjust model to reflect changes
            parent.setName("new name");
            parent.getChildren().remove(0);
            parent.getChildren().get(0).setName("new name 2");
            OTOMStrictChild child = new OTOMStrictChild();
            child.setId(3);
            child.setName("child 3");
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        OTOMStrictParent existing2 = em.find(OTOMStrictParent.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(parent, existing2, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testUpdateNotLoadedParentAndChildren() {

        // create new version of parent and children
        OTOMStrictParent newVersionOfParent = new OTOMStrictParent();
        newVersionOfParent.setId(1);
        newVersionOfParent.setName("parent new name");

        OTOMStrictChild child = new OTOMStrictChild();
        child.setId(3);
        child.setName("child " + 3);
        child.setParent(newVersionOfParent);
        newVersionOfParent.getChildren().add(child);

        // update
        em.merge(newVersionOfParent);
        flushAndClear();

        // verify results
        //observe existing children but not loaded are removed
        ReflectionAssert.assertReflectionEquals(newVersionOfParent, em.find(OTOMStrictParent.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

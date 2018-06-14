package relationships.onetomany.strict;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestUpdateParentWithCascadeToChildren extends TransactionalSetup {

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

        em.persist(parent);
        flushAndClear();
    }

    @Test
    public void test() {

        // create new version of parent and children
        OTOMStrictParent newVersionOfParent = new OTOMStrictParent();
        newVersionOfParent.setId(1);
        newVersionOfParent.setName("parent new name");

        for (int i = 6; i <= 8; i++) {
            OTOMStrictChild child = new OTOMStrictChild();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(newVersionOfParent);
            newVersionOfParent.getChildren().add(child);
        }

        // update
        em.merge(newVersionOfParent);
        flushAndClear();

        // verify results
        //observe existing children are removed
        ReflectionAssert.assertReflectionEquals(newVersionOfParent, em.find(OTOMStrictParent.class, parent.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

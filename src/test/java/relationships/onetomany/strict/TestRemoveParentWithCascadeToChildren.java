package relationships.onetomany.strict;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestRemoveParentWithCascadeToChildren extends TransactionalSetup {

    private OTOMStrictParent parent = buildModel();

    private OTOMStrictParent buildModel() {

        OTOMStrictParent parent = new OTOMStrictParent();
        parent.setId(1);
        parent.setName("parent name");
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
        verifyCorrespondingTableIsEmpty(OTOMStrictParent.class);
        verifyCorrespondingTableIsEmpty(OTOMStrictChild.class);
        em.persist(parent);
        flushAndClear();
    }

    @Test
    public void test() {

        // remove parent
        em.remove(em.find(OTOMStrictParent.class, parent.getId()));
        flushAndClear();

        // verify parent and children are removed
        verifyCorrespondingTableIsEmpty(OTOMStrictParent.class);
        verifyCorrespondingTableIsEmpty(OTOMStrictChild.class);

    }
}

package relationships.onetomany.oneside;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestRemoveParent extends TransactionalSetup {

    private final OTOMOneSideParent parent = buildModel();

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

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(OTOMOneSideParent.class);
        verifyCorrespondingTableIsEmpty(OTOMSOneSideChild.class);
        em.persist(parent);
        flushAndClear();
    }

    @Test
    public void testRemoveParentRemovesAllOrphanedChildren() {

        // remove parent
        em.remove(em.find(OTOMOneSideParent.class, parent.getId()));
        flushAndClear();

        // verify parent and children are removed
        verifyCorrespondingTableIsEmpty(OTOMOneSideParent.class);
        verifyCorrespondingTableIsEmpty(OTOMSOneSideChild.class);

    }

    @Test
    public void testClearParentChildrenListRemovesAllOrphanedChildren() {

        // clear parent children list
        em.find(OTOMOneSideParent.class, parent.getId()).getChildren().clear();
        flushAndClear();

        // verify children are removed
        verifyCorrespondingTableIsEmpty(OTOMSOneSideChild.class);

        parent.getChildren().clear();
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(em.find(OTOMOneSideParent.class, parent.getId()));

    }
}

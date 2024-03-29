package relationships.onetomany.notstrict;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestOrphanChild extends TransactionalSetup {

    private final OTOMNotStrictParent parent = buildModel();

    private OTOMNotStrictParent buildModel() {

        OTOMNotStrictParent parent = new OTOMNotStrictParent();
        parent.setId(1);
        parent.setName("parent name");

        for (int i = 1; i <= 3; i++) {
            OTOMNotStrictChild child = new OTOMNotStrictChild();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @BeforeEach
    public void before() {
        em.persist(parent);
        persist(parent.getChildren());
        flushAndClear();
    }

    @Test
    public void testMakeOrphanChildBySettingParentToNull() {

        OTOMNotStrictChild toRemoveChild = em.find(OTOMNotStrictParent.class, parent.getId()).getChildren().get(1);
        toRemoveChild.setParent(null);// mark child as orphan
        flushAndClear();

        // test parent and other children are unaffected
        OTOMNotStrictParent expectedParent = buildModel();
        expectedParent.getChildren().remove(1);
        org.assertj.core.api.Assertions.assertThat(expectedParent).usingRecursiveComparison().isEqualTo(em.find(OTOMNotStrictParent.class, parent.getId()));

        // test child is now orphan
        OTOMNotStrictChild child = em.find(OTOMNotStrictChild.class, toRemoveChild.getId());
        org.assertj.core.api.Assertions.assertThat(toRemoveChild).usingRecursiveComparison().isEqualTo(child);

    }

    @Test
    public void testMakeOrphanChildByRemovingFromItsParentChildrenListDoesNotMakeTheChildOrphan() {

        em.find(OTOMNotStrictParent.class, parent.getId()).getChildren().remove(1);// remove child from its parent children list
        flushAndClear();

        // test nothing happened
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(em.find(OTOMNotStrictParent.class, parent.getId()));

    }

}

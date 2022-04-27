package relationships.onetomany.notstrict;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestInsertParentWithNoCascadeToChildren extends TransactionalSetup {

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
        verifyCorrespondingTableIsEmpty(OTOMNotStrictParent.class);
        verifyCorrespondingTableIsEmpty(OTOMNotStrictChild.class);
    }

    @Test
    public void test() {

        // persist only the parent
        em.persist(parent);
        flushAndClear();

        // verify children are not persisted
        {// adjust model to reflect expectations
            parent.getChildren().clear();
        }
        OTOMNotStrictParent existing = em.find(OTOMNotStrictParent.class, parent.getId());
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(existing);

    }
}

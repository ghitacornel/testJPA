package relationships.onetomany.strict;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestInsertParentWithCascadeToChildren extends TransactionalSetup {

    private final OTOMStrictParent parent = buildModel();

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

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(OTOMStrictParent.class);
        verifyCorrespondingTableIsEmpty(OTOMStrictChild.class);
    }

    @Test
    public void test() {

        em.persist(parent);
        flushAndClear();

        OTOMStrictParent existing = em.find(OTOMStrictParent.class, parent.getId());
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(existing);

    }
}

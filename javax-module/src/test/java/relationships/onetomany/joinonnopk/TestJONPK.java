package relationships.onetomany.joinonnopk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestJONPK extends TransactionalSetup {

    private final JONPKParent model = buildModel();

    private JONPKParent buildModel() {

        JONPKParent parent = new JONPKParent();
        parent.setId(1);
        parent.setName("parent 1");

        for (int i = 1; i <= 3; i++) {
            JONPKChild child = new JONPKChild();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(JONPKParent.class);
        verifyCorrespondingTableIsEmpty(JONPKChild.class);
    }

    @Test
    public void testSelectAll() {

        em.persist(model);
        flushAndClear();

        JONPKParent existing = em.find(JONPKParent.class, model.getId());
        org.assertj.core.api.Assertions.assertThat(model).usingRecursiveComparison().isEqualTo(existing);
        org.assertj.core.api.Assertions.assertThat(model.getChildren()).usingRecursiveComparison().isEqualTo(existing.getChildren());

    }
}

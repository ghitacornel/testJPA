package relationships.onetoone.childPKequalsparentPK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestPersist extends TransactionalSetup {

    private final PKParent model = buildModel();

    private PKParent buildModel() {

        PKParent parent = new PKParent();
        parent.setId(1);
        parent.setName("parent 1");

        PKChild child = new PKChild();
        child.setId(1);
        child.setName("child " + 1);
        child.setParent(parent);

        return parent;
    }

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(PKParent.class);
        verifyCorrespondingTableIsEmpty(PKChild.class);
    }

    @Test
    public void testSelectAll() {

        em.persist(model);
        flushAndClear();

        PKParent existing = em.find(PKParent.class, model.getId());
        org.assertj.core.api.Assertions.assertThat(model).usingRecursiveComparison().isEqualTo(existing);

    }
}

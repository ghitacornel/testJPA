package relationships.onetomany.oneside;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;
import java.util.Collections;

public class TestInsertParentWithCascadeToChildren extends TransactionalSetup {

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
    }

    @Test
    public void testInsertParentWithCascadeToChildren() {

        em.persist(parent);
        flushAndClear();

        OTOMOneSideParent existing = em.find(OTOMOneSideParent.class, parent.getId());
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(existing);

    }

    @Test
    public void testInsertChildDoesNotWork() {
        OTOMSOneSideChild child = new OTOMSOneSideChild();
        child.setId(1);
        child.setName("child ");

        Assertions.assertThrows(PersistenceException.class, () -> {
            em.persist(child);
            flushAndClear();
        });
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
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(em.find(OTOMOneSideParent.class, parent.getId()));
    }

    @Test
    public void testResetChildrenListDoesNotRemoveOldChildrenAndFails() {

        em.persist(parent);
        flushAndClear();

        OTOMSOneSideChild child = new OTOMSOneSideChild();
        child.setId(11);
        child.setName("child 11");

        Assertions.assertThrows(PersistenceException.class, () -> {
            em.find(OTOMOneSideParent.class, parent.getId()).setChildren(Collections.singletonList(child));
            flushAndClear();
        });
    }
}

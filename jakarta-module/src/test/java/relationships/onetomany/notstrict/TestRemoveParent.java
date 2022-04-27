package relationships.onetomany.notstrict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import jakarta.persistence.PersistenceException;

public class TestRemoveParent extends TransactionalSetup {

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
        em.persist(parent);
        persist(parent.getChildren());
        flushAndClear();
    }

    @Test
    public void testRemoveParentFailsDueToExistingChildren() {
        Assertions.assertThrows(PersistenceException.class, () -> {
            // remove parent
            em.remove(em.find(OTOMNotStrictParent.class, parent.getId()));
            flushAndClear();
        });
    }

    @Test
    public void testRemoveParentRequiresMarkingItsChildrenAsOrphans() {

        OTOMNotStrictParent existing = em.find(OTOMNotStrictParent.class, parent.getId());

        // first make children orphan
        for (OTOMNotStrictChild child : existing.getChildren()) {
            child.setParent(null);
        }

        // second remove the parent
        em.remove(em.find(OTOMNotStrictParent.class, parent.getId()));
        flushAndClear();

        // verify parent is removed
        Assertions.assertNull(em.find(OTOMNotStrictParent.class, this.parent.getId()));

        // verify parent children are orphaned
        {// adjust model to match expectations
            for (OTOMNotStrictChild child : parent.getChildren()) {
                child.setParent(null);
            }
        }
        org.assertj.core.api.Assertions.assertThat(parent.getChildren()).usingRecursiveComparison().isEqualTo(em.createQuery("select t from OTOMNotStrictChild t", OTOMNotStrictChild.class).getResultList());

    }
}

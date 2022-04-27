package relationships.onetomany.strict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import jakarta.persistence.PersistenceException;

public class TestRemoveChild extends TransactionalSetup {

    private final OTOMStrictParent model = buildModel();

    private OTOMStrictParent buildModel() {

        OTOMStrictParent parent = new OTOMStrictParent();
        parent.setId(1);
        parent.setName("parent 1");

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
        em.persist(model);
        flushAndClear();
    }

    @Test
    public void testRemoveChildByRemovingDirectlyFromItsParentChildrenList() {

        em.find(OTOMStrictParent.class, model.getId()).getChildren().remove(1);
        flushAndClear();

        OTOMStrictParent existingParent = em.find(OTOMStrictParent.class, model.getId());
        OTOMStrictParent expectedParent = buildModel();
        {// adjust model to reflect expected changes
            expectedParent.getChildren().remove(1);
        }
        org.assertj.core.api.Assertions.assertThat(expectedParent).usingRecursiveComparison().isEqualTo(existingParent);

        // child is removed since it is now an orphan
        OTOMStrictChild child = em.find(OTOMStrictChild.class, buildModel().getChildren().get(1).getId());
        Assertions.assertNull(child);

    }

    @Test
    public void testRemoveChildByMakingItOrphanDoesNotWorkDueToConstraint() {
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.find(OTOMStrictParent.class, model.getId()).getChildren().get(1).setParent(null);
            flushAndClear();
        });
    }

    @Test
    public void testRemoveChild() {

        em.remove(em.find(OTOMStrictChild.class, 1));
        flushAndClear();

        // test parent and other children are unaffected
        OTOMStrictParent expectedParent = buildModel();
        {// adjust model to reflect expected changes
            expectedParent.getChildren().remove(0);
        }
        org.assertj.core.api.Assertions.assertThat(expectedParent).usingRecursiveComparison().isEqualTo(em.find(OTOMStrictParent.class, expectedParent.getId()));

        // test child is now removed
        Assertions.assertNull(em.find(OTOMStrictChild.class, 1));

    }

    @Test
    public void testRemoveChildWhenParentAndSiblingsAreLoadedDoesNotTriggerTheRemoval() {

        OTOMStrictParent existingParent = em.find(OTOMStrictParent.class, 1);
        Assertions.assertEquals(3, existingParent.getChildren().size());// verify number of children

        OTOMStrictChild toRemoveChild = em.find(OTOMStrictChild.class, 1);
        em.remove(toRemoveChild);// remove only the child
        Assertions.assertEquals(3, existingParent.getChildren().size());// verify number of children is the same
        flushAndClear();

        // test parent and other children are unaffected
        OTOMStrictParent expectedParent = buildModel();
        org.assertj.core.api.Assertions.assertThat(expectedParent).usingRecursiveComparison().isEqualTo(em.find(OTOMStrictParent.class, expectedParent.getId()));

        // test child is now removed
        Assertions.assertNotNull(em.find(OTOMStrictChild.class, toRemoveChild.getId()));

    }

    @Test
    public void testRemoveChildWhenParentAndSiblingsAreLoadedWorks() {

        OTOMStrictParent existingParent = em.find(OTOMStrictParent.class, 1);
        Assertions.assertEquals(3, existingParent.getChildren().size());// verify number of children

        OTOMStrictChild toRemoveChild = em.find(OTOMStrictChild.class, 1);
        existingParent.getChildren().remove(toRemoveChild);// remove link from parent to child
        em.remove(toRemoveChild);// remove the child
        Assertions.assertEquals(2, existingParent.getChildren().size());// verify number of children
        flushAndClear();

        // test parent and other children are unaffected
        OTOMStrictParent expectedParent = buildModel();
        {// adjust model to reflect expected changes
            expectedParent.getChildren().remove(0);
        }
        org.assertj.core.api.Assertions.assertThat(expectedParent).usingRecursiveComparison().isEqualTo(em.find(OTOMStrictParent.class, expectedParent.getId()));

        // test child is now removed
        Assertions.assertNull(em.find(OTOMStrictChild.class, toRemoveChild.getId()));

    }
}

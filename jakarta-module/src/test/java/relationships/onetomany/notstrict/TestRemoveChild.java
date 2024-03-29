package relationships.onetomany.notstrict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestRemoveChild extends TransactionalSetup {

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
    public void testRemoveChild() {

        em.remove(em.find(OTOMNotStrictChild.class, 1));
        flushAndClear();

        // test parent and other children are unaffected
        OTOMNotStrictParent expectedParent = buildModel();
        {// adjust model to reflect expected changes
            expectedParent.getChildren().remove(0);
        }
        org.assertj.core.api.Assertions.assertThat(expectedParent).usingRecursiveComparison().isEqualTo(em.find(OTOMNotStrictParent.class, expectedParent.getId()));

        // test child is now removed
        Assertions.assertNull(em.find(OTOMNotStrictChild.class, 1));

    }

    @Test
    public void testRemoveChildWhenParentAndSiblingsAreLoadedWorksAndDoesNotAffectListOfSiblings() {

        OTOMNotStrictParent existingParent = em.find(OTOMNotStrictParent.class, parent.getId());
        Assertions.assertEquals(3, existingParent.getChildren().size());// verify number of children

        em.remove(em.find(OTOMNotStrictChild.class, 1));
        Assertions.assertEquals(3, existingParent.getChildren().size());// verify number of children is the same
        flushAndClear();

        // test parent and other children are unaffected
        OTOMNotStrictParent expectedParent = buildModel();
        {// adjust model to reflect expected changes
            expectedParent.getChildren().remove(0);
        }
        org.assertj.core.api.Assertions.assertThat(expectedParent).usingRecursiveComparison().isEqualTo(em.find(OTOMNotStrictParent.class, expectedParent.getId()));

        // test child is now removed
        Assertions.assertNull(em.find(OTOMNotStrictChild.class, 1));

    }

}

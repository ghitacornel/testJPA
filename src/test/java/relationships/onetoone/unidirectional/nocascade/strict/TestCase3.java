package relationships.onetoone.unidirectional.nocascade.strict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestCase3 extends TransactionalSetup {

    @Test
    public void test_PersistOnlyTheChild_AndObserve_NoCascadeInsertIsPropagatedToTheParentHenceExceptionIsRaised() {

        Case3Parent parent = new Case3Parent();
        parent.setId(1);
        parent.setName("parent");

        Case3Child child = new Case3Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            // persist only the child
            em.persist(child);
            flushAndClear();
        });
    }

    @Test
    public void test_PersistInProperOrder() {

        Case3Parent parent = new Case3Parent();
        parent.setId(1);
        parent.setName("parent");

        Case3Child child = new Case3Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        // persist first the parent
        em.persist(parent);
        // persist second the child
        em.persist(child);
        flushAndClear();

        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(em.find(Case3Parent.class, 1));
        org.assertj.core.api.Assertions.assertThat(child).usingRecursiveComparison().isEqualTo(em.find(Case3Child.class, 1));

    }

    @Test
    public void test_MergeOnlyTheChild_AndObserve_TheParentIsNotMergedSinceNoCascadeIsInPlace() {

        Case3Parent original = new Case3Parent();
        original.setId(1);
        original.setName("parent");

        {

            Case3Child child = new Case3Child();
            child.setId(1);
            child.setName("child");
            child.setParent(original);

            em.persist(original);
            em.persist(child);
            flushAndClear();

        }

        Case3Parent parent = new Case3Parent();
        parent.setId(1);
        parent.setName("parent new");

        Case3Child child = new Case3Child();
        child.setId(1);
        child.setName("child new");
        child.setParent(parent);

        // merge only the child
        em.merge(child);
        flushAndClear();

        // verify child is merged
        child.setParent(original);
        org.assertj.core.api.Assertions.assertThat(child).usingRecursiveComparison().isEqualTo(em.find(Case3Child.class, 1));
        // verify parent is not merged
        org.assertj.core.api.Assertions.assertThat(original).usingRecursiveComparison().isEqualTo(em.find(Case3Parent.class, 1));

    }

    @Test
    public void test_RemoveTheChild_AndObserve_CascadeDeleteIsPropagatedToTheParentDueToRemoveOrphan() {

        Case3Parent parent = new Case3Parent();
        parent.setId(1);
        parent.setName("parent");

        Case3Child child = new Case3Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(parent);
        em.persist(child);
        flushAndClear();

        // remove only the child
        em.remove(em.find(Case3Child.class, 1));
        flushAndClear();

        Assertions.assertNull(em.find(Case3Child.class, 1));
        Assertions.assertNull(em.find(Case3Parent.class, 1));

    }
}

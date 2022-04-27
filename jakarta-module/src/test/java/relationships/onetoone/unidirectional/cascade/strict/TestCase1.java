package relationships.onetoone.unidirectional.cascade.strict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import jakarta.persistence.PersistenceException;

public class TestCase1 extends TransactionalSetup {

    @Test
    public void test_InsertOnlyTheChild_AndObserve_TheParentIsAlsoInsertedDueToCascade() {

        Case1Parent parent = new Case1Parent();
        parent.setId(1);
        parent.setName("parent");

        Case1Child child = new Case1Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        // insert only the child
        em.persist(child);
        flushAndClear();

        // verify child and parent are both inserted
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(em.find(Case1Parent.class, 1));
        org.assertj.core.api.Assertions.assertThat(child).usingRecursiveComparison().isEqualTo(em.find(Case1Child.class, 1));

    }

    @Test
    public void test_MergeOnlyTheChild_AndObserve_TheParentIsAlsoMergedDueToCascade() {

        {
            Case1Parent parent = new Case1Parent();
            parent.setId(1);
            parent.setName("parent");

            Case1Child child = new Case1Child();
            child.setId(1);
            child.setName("child");
            child.setParent(parent);

            // insert only the child
            em.persist(child);
            flushAndClear();

        }

        Case1Parent parent = new Case1Parent();
        parent.setId(1);
        parent.setName("parent new");

        Case1Child child = new Case1Child();
        child.setId(1);
        child.setName("child new");
        child.setParent(parent);

        // insert only the child
        em.merge(child);
        flushAndClear();

        // verify child and parent are both inserted
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(em.find(Case1Parent.class, 1));
        org.assertj.core.api.Assertions.assertThat(child).usingRecursiveComparison().isEqualTo(em.find(Case1Child.class, 1));

    }

    @Test
    public void test_RemoveOnlyTheChild_AndObserve_TheParentIsAlsoRemovedDueToCascade() {

        Case1Parent parent = new Case1Parent();
        parent.setId(1);
        parent.setName("parent");

        Case1Child child = new Case1Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);
        flushAndClear();

        // remove only the child
        em.remove(em.find(Case1Child.class, 1));
        flushAndClear();

        // verify the parent is also removed
        Assertions.assertNull(em.find(Case1Parent.class, 1));
        Assertions.assertNull(em.find(Case1Child.class, 1));

    }

    @Test
    public void test_RemoveOnlyTheParent_AndObserve_TheCascadeRemovalOfTheOrphanChildIsNotTriggeredHenceHenceExceptionIsRaisedDueToConstraint() {

        Case1Parent parent = new Case1Parent();
        parent.setId(1);
        parent.setName("parent");

        Case1Child child = new Case1Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);
        flushAndClear();

        Assertions.assertThrows(PersistenceException.class, () -> {
            em.remove(em.find(Case1Parent.class, 1));
            flushAndClear();
        });
        // the parent is removed and not the child since the removed parent does not know about the related child
        // in this case JPA won't trigger a select to remove the orphaned child hence exception

    }

    @Test
    public void test_MarkTheParentOfTheChildAsNull_AndObserve_TheCascadeRemovalOfTheOrphanedChildIsNotTriggeredHenceExceptionIsRaisedDueToConstraint() {

        Case1Parent parent = new Case1Parent();
        parent.setId(1);
        parent.setName("parent");

        Case1Child child = new Case1Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);
        flushAndClear();

        Assertions.assertThrows(PersistenceException.class, () -> {
            // remove only the child
            em.find(Case1Child.class, 1).setParent(null);
            flushAndClear();
        });

    }
}

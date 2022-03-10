package relationships.onetoone.unidirectional.cascade.notstrict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;

public class TestCase2 extends TransactionalSetup {

    @Test
    public void test_InsertOnlyTheChild_AndObserve_TheParentIsAlsoInsertedDueToCascade() {

        Case2Parent parent = new Case2Parent();
        parent.setId(1);
        parent.setName("parent");

        Case2Child child = new Case2Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        // insert only the child
        em.persist(child);
        flushAndClear();

        // verify child and parent are both inserted
        ReflectionAssert.assertReflectionEquals(parent, em.find(Case2Parent.class, 1));
        ReflectionAssert.assertReflectionEquals(child, em.find(Case2Child.class, 1));

    }

    @Test
    public void test_MergeOnlyTheChild_AndObserve_TheParentIsAlsoMergedDueToCascade() {

        {
            Case2Parent parent = new Case2Parent();
            parent.setId(1);
            parent.setName("parent");

            Case2Child child = new Case2Child();
            child.setId(1);
            child.setName("child");
            child.setParent(parent);

            // insert only the child
            em.persist(child);
            flushAndClear();

        }

        Case2Parent parent = new Case2Parent();
        parent.setId(1);
        parent.setName("parent new");

        Case2Child child = new Case2Child();
        child.setId(1);
        child.setName("child new");
        child.setParent(parent);

        // insert only the child
        em.merge(child);
        flushAndClear();

        // verify child and parent are both inserted
        ReflectionAssert.assertReflectionEquals(parent, em.find(Case2Parent.class, 1));
        ReflectionAssert.assertReflectionEquals(child, em.find(Case2Child.class, 1));

    }

    @Test
    public void test_RemoveOnlyTheChild_AndObserve_TheParentIsAlsoRemovedDueToCascade() {

        Case2Parent parent = new Case2Parent();
        parent.setId(1);
        parent.setName("parent");

        Case2Child child = new Case2Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);
        flushAndClear();

        // remove only the child
        em.remove(em.find(Case2Child.class, 1));
        flushAndClear();

        // verify the parent is also removed
        Assertions.assertNull(em.find(Case2Parent.class, 1));
        Assertions.assertNull(em.find(Case2Child.class, 1));

    }

    @Test
    public void test_RemoveOnlyTheParent_AndObserve_TheCascadeRemovalOfTheOrphanChildIsNotTriggeredHenceHenceExceptionIsRaisedDueToConstraint() {

        Case2Parent parent = new Case2Parent();
        parent.setId(1);
        parent.setName("parent");

        Case2Child child = new Case2Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);
        flushAndClear();

        Assertions.assertThrows(PersistenceException.class, () -> {
            em.remove(em.find(Case2Parent.class, 1));
            flushAndClear();
        });
        // the parent is removed and not the child since the removed parent does not know about the related child
        // in this case JPA won't trigger a select to remove the orphaned child hence exception

    }

    @Test
    public void test_TheMarkParentOfTheChildAsNull_AndObserve_TheParentIsRemovedDueToCascadeButTheOrphanedChildIsNotRemoved() {

        Case2Parent parent = new Case2Parent();
        parent.setId(1);
        parent.setName("parent");

        Case2Child child = new Case2Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);
        flushAndClear();

        // mark the parent as null
        em.find(Case2Child.class, 1).setParent(null);
        flushAndClear();

        Assertions.assertNull(em.find(Case2Parent.class, 1));
        child.setParent(null);
        ReflectionAssert.assertReflectionEquals(child, em.find(Case2Child.class, 1));

    }
}

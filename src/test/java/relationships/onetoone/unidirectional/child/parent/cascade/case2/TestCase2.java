package relationships.onetoone.unidirectional.child.parent.cascade.case2;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

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
        Assert.assertNull(em.find(Case2Parent.class, 1));
        Assert.assertNull(em.find(Case2Child.class, 1));

    }

    @Test(expected = javax.persistence.PersistenceException.class)
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

        em.remove(em.find(Case2Parent.class, 1));
        flushAndClear();
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

        Assert.assertNull(em.find(Case2Parent.class, 1));
        child.setParent(null);
        ReflectionAssert.assertReflectionEquals(child, em.find(Case2Child.class, 1));

    }
}

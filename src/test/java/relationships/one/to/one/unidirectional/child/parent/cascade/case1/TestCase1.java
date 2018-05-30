package relationships.one.to.one.unidirectional.child.parent.cascade.case1;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestCase1 extends TransactionalSetup {

    @Test
    public void testInsertOnlyChildAndObserveCascadeInsertToParent() {

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
        ReflectionAssert.assertReflectionEquals(parent, em.find(Case1Parent.class, 1));
        ReflectionAssert.assertReflectionEquals(child, em.find(Case1Child.class, 1));

    }

    @Test
    public void testRemoveOnlyTheChildAndObserveCascadeDeleteToParent() {

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
        Assert.assertNull(em.find(Case1Parent.class, 1));
        Assert.assertNull(em.find(Case1Child.class, 1));

    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testRemoveOnlyTheParentAndObserveCascadeToChildIsNotTriggeredHenceTheFailToRemoveTheOrphanChild() {

        Case1Parent parent = new Case1Parent();
        parent.setId(1);
        parent.setName("parent");

        Case1Child child = new Case1Child();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);
        flushAndClear();

        em.remove(em.find(Case1Parent.class, 1));
        flushAndClear();
        // the parent is removed and not the child since the removed parent does not know about the related child
        // in this case JPA won't trigger a select to remove the orphaned child hence exception

    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testMarkParentOfTheChildAsNullAndObserveTheOrphanedChildIsNotRemovedAndExceptionIsRaisedDueToConstraint() {

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
        em.find(Case1Child.class, 1).setParent(null);
        flushAndClear();

    }
}

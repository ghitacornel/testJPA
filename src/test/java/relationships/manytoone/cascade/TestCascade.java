package relationships.manytoone.cascade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;

public class TestCascade extends TransactionalSetup {

    @BeforeEach
    public void setUp() {
        verifyCorrespondingTableIsEmpty(MTOOCascadeParent.class);
        verifyCorrespondingTableIsEmpty(MTOOCascadeChild.class);
    }

    @Test
    public void testCascadePersistFromChildToParent() {

        MTOOCascadeParent parent = new MTOOCascadeParent();
        parent.setId(1);
        parent.setName("parent");

        MTOOCascadeChild child = new MTOOCascadeChild();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        // persist only the child
        em.persist(child);
        flushAndClear();

        // verify both parent and child are persisted
        ReflectionAssert.assertReflectionEquals(child, em.find(MTOOCascadeChild.class, child.getId()));
        ReflectionAssert.assertReflectionEquals(parent, em.find(MTOOCascadeParent.class, parent.getId()));

    }

    @Test
    public void testCascadeRemoveFromChildToParent() {

        MTOOCascadeParent parent = new MTOOCascadeParent();
        parent.setId(1);
        parent.setName("parent");

        MTOOCascadeChild child = new MTOOCascadeChild();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        // persist
        em.persist(child);
        flushAndClear();

        // remove only the child
        em.remove(em.find(MTOOCascadeChild.class, child.getId()));
        flushAndClear();

        // verify both parent and child are removed
        // it works because the parent is referenced by a single child already removed
        verifyCorrespondingTableIsEmpty(MTOOCascadeParent.class);
        verifyCorrespondingTableIsEmpty(MTOOCascadeChild.class);

    }

    @Test
    public void testCascadeRemoveChildLeadsToRemovalOfParentReferencedByAnotherChildAlso() {

        MTOOCascadeParent parent = new MTOOCascadeParent();
        parent.setId(1);
        parent.setName("parent");

        MTOOCascadeChild child1 = new MTOOCascadeChild();
        child1.setId(1);
        child1.setName("child 1");
        child1.setParent(parent);

        MTOOCascadeChild child2 = new MTOOCascadeChild();
        child2.setId(2);
        child2.setName("child 2");
        child2.setParent(parent);

        // persist
        em.persist(child1);
        em.persist(child2);
        flushAndClear();

        // remove only first child
        // observe exception raised due to having a second child still referencing the removed parent
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.remove(em.find(MTOOCascadeChild.class, child1.getId()));
            flushAndClear();
        });
    }
}

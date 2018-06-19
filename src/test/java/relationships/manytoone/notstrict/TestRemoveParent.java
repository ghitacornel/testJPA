package relationships.manytoone.notstrict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;

public class TestRemoveParent extends TransactionalSetup {

    private MTOONotStrictParent parent1 = new MTOONotStrictParent();
    private MTOONotStrictParent parent2 = new MTOONotStrictParent();

    @Before
    public void before() {

        verifyCorrespondingTableIsEmpty(MTOONotStrictParent.class);
        verifyCorrespondingTableIsEmpty(MTOONotStrictChild.class);

        parent1.setId(1);
        parent1.setName("parent 1");
        persist(parent1);

        parent2.setId(2);
        parent2.setName("parent 2");
        persist(parent2);

        MTOONotStrictChild child = new MTOONotStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent2);
        em.persist(child);

        flushAndClear();
    }

    @Test
    public void testRemoveParentWithNoChildren() {


        //  remove parent with no children
        em.remove(em.find(MTOONotStrictParent.class, parent1.getId()));
        flushAndClear();

        // test remove
        Assert.assertNull(em.find(MTOONotStrictParent.class, parent1.getId()));

        // verify the other parent is unaffected
        ReflectionAssert.assertReflectionEquals(parent2, em.find(MTOONotStrictParent.class, parent2.getId()));

    }

    /**
     * in this case an exception is raised due to having children referencing the removed parent<br>
     * even if the relationship is not a strict one JPA does not mark existing children as orphans having their
     * reference to their parent set to null
     */
    @Test(expected = PersistenceException.class)
    public void testRemoveParentWithChildren() {

        //  remove parent with children
        em.remove(em.find(MTOONotStrictParent.class, parent2.getId()));
        flushAndClear();

    }

}

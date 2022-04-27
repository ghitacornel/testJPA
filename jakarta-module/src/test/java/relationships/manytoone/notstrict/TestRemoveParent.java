package relationships.manytoone.notstrict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import jakarta.persistence.PersistenceException;

public class TestRemoveParent extends TransactionalSetup {

    private final MTOONotStrictParent parent1 = new MTOONotStrictParent();
    private final MTOONotStrictParent parent2 = new MTOONotStrictParent();

    @BeforeEach
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
        Assertions.assertNull(em.find(MTOONotStrictParent.class, parent1.getId()));

        // verify the other parent is unaffected
        org.assertj.core.api.Assertions.assertThat(parent2).usingRecursiveComparison().isEqualTo(em.find(MTOONotStrictParent.class, parent2.getId()));

    }

    /**
     * in this case an exception is raised due to having children referencing the removed parent<br>
     * even if the relationship is not a strict one JPA does not mark existing children as orphans having their
     * reference to their parent set to null
     */
    @Test
    public void testRemoveParentWithChildren() {
        Assertions.assertThrows(PersistenceException.class, () -> {
            //  remove parent with children
            em.remove(em.find(MTOONotStrictParent.class, parent2.getId()));
            flushAndClear();
        });
    }

}

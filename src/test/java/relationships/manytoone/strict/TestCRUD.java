package relationships.manytoone.strict;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.List;

public class TestCRUD extends TransactionalSetup {

    private MTOOStrictParent parent1 = new MTOOStrictParent();
    private MTOOStrictParent parent2 = new MTOOStrictParent();

    @BeforeEach
    public void before() {

        parent1.setId(1);
        parent1.setName("parent strict 1");
        persist(parent1);

        parent2.setId(2);
        parent2.setName("parent strict 2");
        persist(parent2);

        flushAndClear();

    }

    @Test
    public void testCRUD() {

        // verify no child is present
        verifyCorrespondingTableIsEmpty(MTOOStrictChild.class);

        // insert
        MTOOStrictChild child = new MTOOStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent1);
        em.persist(child);
        flushAndClear();

        // test insert
        List<MTOOStrictChild> list = em.createQuery("select t from MTOOStrictChild t", MTOOStrictChild.class).getResultList();
        Assertions.assertEquals(1, list.size());
        org.assertj.core.api.Assertions.assertThat(child).usingRecursiveComparison().isEqualTo(list.get(0));
        flushAndClear();

        // update
        MTOOStrictChild existing = em.find(MTOOStrictChild.class, child.getId());
        existing.setName("new child name");// update name
        existing.setParent(parent2);// update parent
        em.merge(existing);
        flushAndClear();

        // test update
        existing = em.find(MTOOStrictChild.class, child.getId());
        Assertions.assertEquals("new child name", existing.getName());
        org.assertj.core.api.Assertions.assertThat(parent2).usingRecursiveComparison().isEqualTo(existing.getParent());
        flushAndClear();

        // remove
        existing = em.find(MTOOStrictChild.class, child.getId());
        Assertions.assertNotNull(existing);
        em.remove(existing);
        flushAndClear();

        // test remove
        verifyCorrespondingTableIsEmpty(MTOOStrictChild.class);

        // verify parents are unaffected
        org.assertj.core.api.Assertions.assertThat(parent1).usingRecursiveComparison().isEqualTo(em.find(MTOOStrictParent.class, parent1.getId()));
        org.assertj.core.api.Assertions.assertThat(parent2).usingRecursiveComparison().isEqualTo(em.find(MTOOStrictParent.class, parent2.getId()));
    }

}

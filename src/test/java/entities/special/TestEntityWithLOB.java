package entities.special;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestEntityWithLOB extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithLOB.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithLOB entity1 = new EntityWithLOB();
        entity1.setId(1);
        entity1.setFileContent(new byte[]{1, 2, 3});
        entity1.setLargeText("qwertyuiop");

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify persist
        EntityWithLOB entity2 = em.find(EntityWithLOB.class, 1);
        Assertions.assertNotNull(entity2);
        org.assertj.core.api.Assertions.assertThat(entity1).usingRecursiveComparison().isEqualTo(entity2);

        // update
        entity2.setFileContent(new byte[]{4, 5, 6});
        entity2.setLargeText("asdfghjkl");
        flushAndClear();

        // verify update
        EntityWithLOB entity3 = em.find(EntityWithLOB.class, 1);
        Assertions.assertNotNull(entity3);
        org.assertj.core.api.Assertions.assertThat(entity2).usingRecursiveComparison().isEqualTo(entity3);

    }
}

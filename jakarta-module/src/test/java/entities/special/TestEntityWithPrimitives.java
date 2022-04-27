package entities.special;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestEntityWithPrimitives extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithPrimitives.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithPrimitives initialEntity = new EntityWithPrimitives();
        initialEntity.setId(1);

        // persist
        em.persist(initialEntity);
        flushAndClear();

        // verify defaults are persisted as expected
        EntityWithPrimitives persistedEntity = em.find(EntityWithPrimitives.class, initialEntity.getId());
        Assertions.assertNotNull(persistedEntity);
        Assertions.assertFalse(persistedEntity.isaBoolean());
        Assertions.assertEquals(3, persistedEntity.getAnInt());

    }
}


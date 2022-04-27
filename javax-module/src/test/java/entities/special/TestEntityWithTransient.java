package entities.special;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestEntityWithTransient extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithTransient.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithTransient initialEntity = new EntityWithTransient();
        initialEntity.setId(1);
        initialEntity.setTransientBoolean(true);
        initialEntity.setTransientInteger(3);

        // persist
        em.persist(initialEntity);
        flushAndClear();

        // verify transients are not persisted as expected
        EntityWithTransient persistedEntity = em.find(EntityWithTransient.class, initialEntity.getId());
        Assertions.assertNotNull(persistedEntity);
        Assertions.assertNull(persistedEntity.getTransientBoolean());
        Assertions.assertNull(persistedEntity.getTransientInteger());

    }
}


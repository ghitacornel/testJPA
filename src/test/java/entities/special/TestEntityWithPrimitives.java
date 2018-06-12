package entities.special;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestEntityWithPrimitives extends TransactionalSetup {

    @Before
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
        Assert.assertNotNull(persistedEntity);
        Assert.assertFalse(persistedEntity.isaBoolean());
        Assert.assertEquals(3, persistedEntity.getAnInt());

    }
}


package entities.special;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestEntityWithTransient extends TransactionalSetup {

    @Before
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
        Assert.assertNotNull(persistedEntity);
        Assert.assertNull(persistedEntity.getTransientBoolean());
        Assert.assertNull(persistedEntity.getTransientInteger());

    }
}


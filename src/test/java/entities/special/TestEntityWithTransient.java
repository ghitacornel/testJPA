package entities.special;

import entities.simple.special.EntityWithTransient;
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
        EntityWithTransient entity1 = new EntityWithTransient();
        entity1.setId(1);
        entity1.setTransientBoolean(true);
        entity1.setTransientInteger(3);

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify transients are not persisted as expected
        EntityWithTransient entity2 = em.find(EntityWithTransient.class, 1);
        Assert.assertNotNull(entity2);
        Assert.assertNull(entity2.getTransientBoolean());
        Assert.assertNull(entity2.getTransientInteger());

    }
}


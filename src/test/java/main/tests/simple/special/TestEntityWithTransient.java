package main.tests.simple.special;

import entities.simple.special.EntityWithTransient;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestEntityWithTransient extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from EntityWithTransient t").getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entity
        EntityWithTransient entity1 = new EntityWithTransient();
        entity1.setId(1);
        entity1.setaBoolean(true);
        entity1.setAnInt(3);

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify transients are not persisted as expected
        EntityWithTransient entity2 = em.find(EntityWithTransient.class, 1);
        Assert.assertNotNull(entity2);
        Assert.assertNull(entity2.getaBoolean());
        Assert.assertNull(entity2.getAnInt());

    }
}


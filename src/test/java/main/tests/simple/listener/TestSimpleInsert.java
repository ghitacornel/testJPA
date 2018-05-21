package main.tests.simple.listener;

import entities.simple.listener.EntityWithListener;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSimpleInsert extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertNull(em.find(EntityWithListener.class, 1));
    }

    @Test
    public void test() {

        // create new entity
        EntityWithListener initialEntity = new EntityWithListener();
        initialEntity.setId(1);
        initialEntity.setName("name");

        // persist
        em.persist(initialEntity);
        flushAndClear();

        // verify
        EntityWithListener entity2 = em.find(EntityWithListener.class, 1);
        Assert.assertNotNull(entity2);
        Assert.assertEquals(entity2.getId(), initialEntity.getId());
        Assert.assertEquals(entity2.getName(), initialEntity.getName());

        // verify listener filled fields
        Assert.assertEquals("prePersist", entity2.getPrePersist());
        Assert.assertNull(entity2.getPreUpdate());

        // update the entity
        entity2.setName("new name");
        em.merge(entity2);
        flushAndClear();

        // verify
        EntityWithListener entity3 = em.find(EntityWithListener.class, 1);
        Assert.assertNotNull(entity3);
        Assert.assertEquals(entity3.getId(), initialEntity.getId());
        Assert.assertEquals(entity3.getName(), entity2.getName());

        // verify listener filled fields
        Assert.assertEquals("prePersist", entity3.getPrePersist());
        Assert.assertEquals("preUpdate", entity3.getPreUpdate());

    }

}

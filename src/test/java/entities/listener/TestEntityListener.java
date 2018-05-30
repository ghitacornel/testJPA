package entities.listener;

import entities.listener.EntityWithListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestEntityListener extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithListener.class);
    }

    @Test
    public void testListenerIsInvoked() {

        // create new entity
        EntityWithListener entity1 = new EntityWithListener();
        entity1.setId(1);
        entity1.setName("name");

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify
        EntityWithListener entity2 = em.find(EntityWithListener.class, 1);
        Assert.assertNotNull(entity2);
        Assert.assertEquals(entity2.getId(), entity1.getId());
        Assert.assertEquals(entity2.getName(), entity1.getName());

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
        Assert.assertEquals(entity3.getId(), entity1.getId());
        Assert.assertEquals(entity3.getName(), entity2.getName());

        // verify listener filled fields
        Assert.assertEquals("prePersist", entity3.getPrePersist());
        Assert.assertEquals("preUpdate", entity3.getPreUpdate());

    }

}

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
        EntityWithListener entitate = new EntityWithListener();
        entitate.setId(1);
        entitate.setName("name");

        // specify null to ensure the entity is
        // persisted with null value for this
        // property
        entitate.setCreationDate(null);

        // persist
        em.persist(entitate);

        // mandatory clear cache (entity managers act as caches also)
        flushAndClear();

        // verify
        EntityWithListener persisted = em.find(EntityWithListener.class, 1);
        Assert.assertNotNull(persisted);
        Assert.assertEquals(entitate.getId(), persisted.getId());
        Assert.assertEquals(entitate.getName(), persisted.getName());

        // the listeners fills this field
        Assert.assertNotNull(persisted.getCreationDate());

    }

}

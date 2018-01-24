package main.tests.ids.listener;

import entities.ids.listener.EntityWithIdListener;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.List;

public class TestIdListener extends TransactionalSetup {

    private static final String SELECT_ALL = "select t from EntityWithIdListener t";

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery(SELECT_ALL).getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entity
        EntityWithIdListener model = new EntityWithIdListener();
        model.setName("name");
        Assert.assertNull(model.getId());

        // persist
        em.persist(model);

        // mandatory clear cache (entity managers act as caches also)
        flushAndClear();

        // verify
        List<EntityWithIdListener> list = em.createQuery(SELECT_ALL, EntityWithIdListener.class).getResultList();
        Assert.assertEquals(1, list.size());
        EntityWithIdListener existing = list.get(0);

        Assert.assertNotNull(existing.getId());
        ReflectionAssert.assertReflectionEquals(model, existing);
    }

}

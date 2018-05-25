package entities.ids.listener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityWithIdListener extends TransactionalSetup {

    private static final String SELECT_ALL = "select t from EntityWithIdListener t";

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery(SELECT_ALL).getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entity
        EntityWithIdListener model = new EntityWithIdListener();
        Assert.assertNull(model.getId());

        // persist
        em.persist(model);
        flushAndClear();

        // verify
        List<EntityWithIdListener> list = em.createQuery(SELECT_ALL, EntityWithIdListener.class).getResultList();
        Assert.assertEquals(1, list.size());
        EntityWithIdListener existing = list.get(0);

        Assert.assertNotNull(existing.getId());
        ReflectionAssert.assertReflectionEquals(model, existing);
    }

}

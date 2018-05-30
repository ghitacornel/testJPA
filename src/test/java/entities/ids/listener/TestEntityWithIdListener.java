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
        verifyCorrespondingTableIsEmpty(EntityWithIdListener.class);
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

        // verify exactly 1 object was persisted
        List<EntityWithIdListener> list = em.createQuery(SELECT_ALL, EntityWithIdListener.class).getResultList();
        Assert.assertEquals(1, list.size());
        EntityWithIdListener existing = list.get(0);

        Assert.assertNotNull(existing.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(model, existing);
    }

}

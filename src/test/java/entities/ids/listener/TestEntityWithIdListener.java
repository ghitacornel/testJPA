package entities.ids.listener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityWithIdListener extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithIdListener.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithIdListener initialEntity = new EntityWithIdListener();
        Assert.assertNull(initialEntity.getId());

        // persist
        em.persist(initialEntity);
        flushAndClear();

        // verify exactly 1 object was persisted
        List<EntityWithIdListener> list = em.createQuery("select t from EntityWithIdListener t", EntityWithIdListener.class).getResultList();
        Assert.assertEquals(1, list.size());
        EntityWithIdListener persistedEntity = list.get(0);

        Assert.assertNotNull(persistedEntity.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(initialEntity, persistedEntity);
    }

}

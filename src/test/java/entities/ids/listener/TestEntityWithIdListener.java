package entities.ids.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityWithIdListener extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithIdListener.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithIdListener initialEntity = new EntityWithIdListener();
        Assertions.assertNull(initialEntity.getId());

        // persist
        em.persist(initialEntity);
        flushAndClear();

        // verify exactly 1 object was persisted
        List<EntityWithIdListener> list = em.createQuery("select t from EntityWithIdListener t", EntityWithIdListener.class).getResultList();
        Assertions.assertEquals(1, list.size());
        EntityWithIdListener persistedEntity = list.get(0);

        Assertions.assertNotNull(persistedEntity.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(initialEntity, persistedEntity);
    }

}

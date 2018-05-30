package entities.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestReInsertSameEntityWithAnotherID extends TransactionalSetup {

    @Before
    public void verifyDatabaseState() {
        Assert.assertTrue(em.createQuery("select t from Entity t").getResultList().isEmpty());
    }

    @Test
    public void testReuseEntity() {

        // create new entity
        Entity entity = new Entity();
        entity.setName("name");

        // persist with id 1
        entity.setId(1);
        em.persist(entity);
        flushAndClear();

        // detach
        em.detach(entity);
        flushAndClear();

        // try to persist it again with id 2
        entity.setId(2);
        em.persist(entity);
        flushAndClear();

        // verify 2
        Entity persisted2 = em.find(Entity.class, 2);
        Assert.assertNotNull(persisted2);
        ReflectionAssert.assertReflectionEquals(entity, persisted2);

        // verify 1
        Entity persisted1 = em.find(Entity.class, 1);
        Assert.assertNotNull(persisted1);
        entity.setId(1);// set the old id prior to checking
        ReflectionAssert.assertReflectionEquals(entity, persisted1);

    }

}

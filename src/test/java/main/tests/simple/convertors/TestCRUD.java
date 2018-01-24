package main.tests.simple.convertors;

import entities.simple.convertors.EntityWithConvertors;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class TestCRUD extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from EntityWithConvertors t").getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entity
        EntityWithConvertors entity1 = new EntityWithConvertors();
        entity1.setId(1);
        entity1.setBooleanValue(true);
        entity1.setPassword("secret1");

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify
        EntityWithConvertors entity2 = em.find(EntityWithConvertors.class, entity1.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

        // update
        entity2.setBooleanValue(false);
        entity1.setPassword("secret2");
        em.merge(entity2);
        flushAndClear();

        // verify
        EntityWithConvertors entity3 = em.find(EntityWithConvertors.class, entity1.getId());
        Assert.assertNotNull(entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

        // delete
        em.remove(entity3);
        flushAndClear();

        // verify
        Assert.assertNull(em.find(EntityWithConvertors.class, entity3.getId()));

    }

}

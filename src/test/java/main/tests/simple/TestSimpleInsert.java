package main.tests.simple;

import entities.simple.Entity;
import entities.simple.SimpleEnum;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class TestSimpleInsert extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from Entity t").getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");
        entity.setBooleanValue(true);
        entity.setFileContent(new byte[]{1, 2, 3});

        // persist
        em.persist(entity);
        flushAndClear();

        // verify
        Entity persisted = em.find(Entity.class, entity.getId());
        Assert.assertNotNull(persisted);
        ReflectionAssert.assertReflectionEquals(entity, persisted);

    }

}

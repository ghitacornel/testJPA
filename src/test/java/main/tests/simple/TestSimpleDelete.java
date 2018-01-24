package main.tests.simple;

import entities.simple.Entity;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSimpleDelete extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from Entity t").getResultList().isEmpty());
        em.persist(buildModel());
        flushAndClear();
    }

    private Entity buildModel() {
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");
        return entity;
    }

    @Test
    public void test() {

        Entity model = buildModel();

        // get model
        Entity entity = em.find(Entity.class, model.getId());
        Assert.assertNotNull(entity);

        // delete from database
        em.remove(entity);

        // mandatory clear cache (entity managers act as caches also)
        flushAndClear();

        // verify
        Assert.assertTrue(em.createQuery("select t from Entity t").getResultList().isEmpty());

    }

}

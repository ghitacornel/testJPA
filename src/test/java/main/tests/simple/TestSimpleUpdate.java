package main.tests.simple;

import entities.simple.Entity;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class TestSimpleUpdate extends TransactionalSetup {

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
        entity.setFileContent(new byte[]{1, 2, 3});
        return entity;
    }

    @Test
    public void test() {

        Entity model = buildModel();

        // get model
        Entity entity = em.find(Entity.class, model.getId());
        Assert.assertNotNull(entity);

        // update model
        entity.setName("newName");

        // XXX Lazy loading on attributes does not work
        boolean loaded = entityManagerFactory.getPersistenceUnitUtil().isLoaded(entity, "fileContent");
        Assert.assertTrue(loaded);

        entity.setFileContent(new byte[]{4, 5, 6});

        // update database
        em.merge(entity);
        flushAndClear();

        // verify
        Entity persisted = em.find(Entity.class, model.getId());
        Assert.assertNotNull(persisted);
        ReflectionAssert.assertReflectionEquals(entity, persisted);

    }

}

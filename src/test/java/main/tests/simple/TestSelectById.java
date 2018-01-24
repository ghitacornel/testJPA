package main.tests.simple;

import entities.simple.Entity;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class TestSelectById extends TransactionalSetup {

    @Before
    public void before() {
        Entity model = buildModel();
        Assert.assertNull(em.find(Entity.class, model.getId()));
        persist(model);
        flushAndClear();
    }

    private Entity buildModel() {
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");
        return entity;
    }

    @Test
    public void test1() {

        // fetch
        Entity entity = em.createQuery("select t from Entity t where t.id = :id", Entity.class)
                .setParameter("id", 1).getSingleResult();

        // verify
        Assert.assertNotNull(entity);
        ReflectionAssert.assertReflectionEquals(entity, buildModel());

    }

    @Test
    public void test2() {

        // fetch
        Entity entity = em.find(Entity.class, 1);

        // verify
        Assert.assertNotNull(entity);
        ReflectionAssert.assertReflectionEquals(entity, buildModel());

    }

}

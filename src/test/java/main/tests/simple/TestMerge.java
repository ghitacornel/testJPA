package main.tests.simple;

import entities.simple.Entity;
import main.tests.Setup;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class TestMerge extends Setup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from Entity t").getResultList().isEmpty());
        flushAndClear();

        // make sure we have some data on a different transaction
        {
            em.getTransaction().begin();
            em.persist(buildModel());
            em.getTransaction().commit();
        }

    }

    /**
     * make sure we clean the database on a different transaction
     */
    @After
    public void after() {
        em.getTransaction().begin();
        em.createQuery("delete from Entity t").executeUpdate();
        em.getTransaction().commit();
    }

    private Entity buildModel() {
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");
        return entity;
    }

    @Test
    public void testMergeNotManagedEntity() {

        // witness object
        Entity model = buildModel();

        // begin transaction
        em.getTransaction().begin();

        // update model
        Entity entity = buildModel();// original updated model is detached
        entity.setName("newName1");

        // merge model to the database
        Entity entity1 = em.merge(entity);
        em.flush();

        // a second object is created and attached to the persistence context
        // since the original updated model is detached
        Assert.assertNotSame(entity1, entity);

        // make further changes on the not managed instance
        entity.setName("newName2");

        // commit at the end
        em.getTransaction().commit();

        // verify on a different transaction that the merge worked
        {
            em.getTransaction().begin();
            Entity updated = em.find(Entity.class, model.getId());
            Assert.assertNotNull(updated);
            ReflectionAssert.assertReflectionEquals(entity1, updated);
            em.getTransaction().commit();
        }

    }

    @Test
    public void testMergeAlreadyManagedEntity() {

        // witness object
        Entity model = buildModel();

        // begin transaction
        em.getTransaction().begin();

        // update model
        Entity entity = em.find(Entity.class, model.getId());
        entity.setName("newName1");

        // merge model to the database
        Entity entity1 = em.merge(entity);
        em.flush();

        // a second object is not created since the original updated model is fetched first
        // hence already attached to the persistence context
        Assert.assertSame(entity1, entity);

        // make further changes on the not managed instance
        entity.setName("newName2");

        // commit at the end
        em.getTransaction().commit();

        // verify on a different transaction that the merge worked
        {
            em.getTransaction().begin();
            Entity updated = em.find(Entity.class, model.getId());
            Assert.assertNotNull(updated);
            ReflectionAssert.assertReflectionEquals(entity, updated);
            em.getTransaction().commit();
        }

    }

}

package main.tests.simple;

import entities.simple.Entity;
import entities.simple.SimpleEnum;
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
        entity.setEnum1(SimpleEnum.ONE);
        entity.setEnum2(SimpleEnum.TWO);
        entity.setBooleanValue(true);
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
        entity.setEnum1(SimpleEnum.THREE);
        entity.setEnum2(SimpleEnum.THREE);
        entity.setBooleanValue(false);

        // merge model to the database
        Entity entity1 = em.merge(entity);
        em.flush();

        // a second object is created and attached to the persistence context
        // since the original updated model is detached
        Assert.assertFalse(entity1 == entity);

        // make further changes on the not managed instance
        entity.setName("newName2");
        entity.setEnum1(SimpleEnum.ONE);
        entity.setEnum2(SimpleEnum.ONE);

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

        // verify the changes performed on the not managed instance are not persisted
        {
            Assert.assertFalse(entity1.getEnum1().equals(entity.getEnum1()));
            Assert.assertFalse(entity1.getEnum2().equals(entity.getEnum2()));
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
        entity.setEnum1(SimpleEnum.THREE);
        entity.setEnum2(SimpleEnum.THREE);
        entity.setBooleanValue(false);

        // merge model to the database
        Entity entity1 = em.merge(entity);
        em.flush();

        // a second object is not created since the original updated model is fetched first
        // hence already attached to the persistence context
        Assert.assertTrue(entity1 == entity);

        // make further changes on the not managed instance
        entity.setName("newName2");
        entity.setEnum1(SimpleEnum.ONE);
        entity.setEnum2(SimpleEnum.ONE);

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

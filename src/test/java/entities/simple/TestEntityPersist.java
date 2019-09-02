package entities.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityPersist extends TransactionalSetup {

    @Before
    public void verifyDatabaseState() {
        verifyCorrespondingTableIsEmpty(Entity.class);
    }

    @Test
    public void testPersistNewEntityUsingPERSIST() {

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");

        // persist
        em.persist(entity);
        flushAndClear();

        // verify persist
        ReflectionAssert.assertReflectionEquals(entity, em.find(Entity.class, entity.getId()));

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id, name, nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("name", objects[1]);
                Assert.assertNull(objects[2]);
            }
        }

    }

    @Test
    public void testPersistNewEntityUsingMERGE() {

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");

        // merge
        Entity mergedEntity = em.merge(entity);
        flushAndClear();

        // verify merge return value
        Assert.assertNotSame(entity, mergedEntity);

        // verify merge
        ReflectionAssert.assertReflectionEquals(entity, em.find(Entity.class, entity.getId()));

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id, name, nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("name", objects[1]);
                Assert.assertNull(objects[2]);
            }
        }

    }

    /**
     * persist + flush + clear + persist again an already persisted but not managed entity => exception
     */
    @Test(expected = javax.persistence.PersistenceException.class)
    public void testPersistMultipleTimesWithOrWithoutFlushButWithAtLeastOneClearLeadsToException() {

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");

        // persist once
        em.persist(entity);
        // then flush
        em.flush();
        // then clear
        em.clear();
        // then persist twice
        em.persist(entity);

        flushAndClear();

    }

    /**
     * persist multiple times same entity with or without flush but with no "clear" => only 1 SQL INSERT is executed
     */
    @Test
    public void testPersistMultipleTimesWithOrWithoutFlushButWithNoClearLeadsToSingleInsert() {

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");

        // persist once
        em.persist(entity);
        // persist again
        em.persist(entity);
        // can even flush
        em.flush();
        // persist again
        em.persist(entity);

        flushAndClear();

        // verify persist
        ReflectionAssert.assertReflectionEquals(entity, em.find(Entity.class, entity.getId()));

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id, name, nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("name", objects[1]);
                Assert.assertNull(objects[2]);
            }
        }

    }

    @Test
    public void testPersistNewEntityUsingPERSISTLeadsToCreationOfManagedEntity() {

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");

        // persist
        em.persist(entity);
        em.flush();

        // update now the managed newly persisted entity
        entity.setName("new name");
        flushAndClear();

        // verify new changes to the managed entity are propagated to the database
        ReflectionAssert.assertReflectionEquals(entity, em.find(Entity.class, entity.getId()));

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id, name, nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("new name", objects[1]);
                Assert.assertNull(objects[2]);
            }
        }

    }

    @Test
    public void testUsingCLEARMakesManagedEntitiesNotManagedAnymore() {

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");

        // persist
        em.persist(entity);
        em.flush();

        // CLEAR persistence context => all entities become not managed
        em.clear();

        // update now the not managed entity
        entity.setName("new name");
        flushAndClear();

        // verify new changes to the not managed entity are not propagated to the database
        {// adjust model to reflect expected changes
            entity.setName("name");
        }
        ReflectionAssert.assertReflectionEquals(entity, em.find(Entity.class, entity.getId()));

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id, name, nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("name", objects[1]);
                Assert.assertNull(objects[2]);
            }
        }

    }

}

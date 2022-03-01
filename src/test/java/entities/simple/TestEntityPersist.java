package entities.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;
import java.util.List;

public class TestEntityPersist extends TransactionalSetup {

    @BeforeEach
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
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("name", objects[1]);
                Assertions.assertNull(objects[2]);
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
        Assertions.assertNotSame(entity, mergedEntity);

        // verify merge
        ReflectionAssert.assertReflectionEquals(entity, em.find(Entity.class, entity.getId()));

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id, name, nullableValue from SimpleEntity t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("name", objects[1]);
                Assertions.assertNull(objects[2]);
            }
        }

    }

    // verify SQL insert is not invoked
    // data validation is performed on java model, not on the database
    @Test
    public void testPersistWithNullValueForNotNullFieldDoesNotTriggerSQLINSERT() {
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName(null);
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.persist(entity);
        });
    }

    /**
     * persist + flush + clear + persist again an already persisted but not managed entity => exception
     */
    @Test
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
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.persist(entity);
            flushAndClear();
        });

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
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("name", objects[1]);
                Assertions.assertNull(objects[2]);
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
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("new name", objects[1]);
                Assertions.assertNull(objects[2]);
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
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("name", objects[1]);
                Assertions.assertNull(objects[2]);
            }
        }

    }

}

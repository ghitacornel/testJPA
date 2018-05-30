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
    public void testPersist() {

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName("name");

        // verify database state with a native query
        {
            Assert.assertTrue(em.createNativeQuery("select * from SimpleEntity").getResultList().isEmpty());
        }

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify persist
        Entity entity2 = em.find(Entity.class, entity1.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

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

    @Test(expected = javax.persistence.PersistenceException.class)
    public void test_PersistOnceThenFlushThenFlushAndClearThePersistenceContextThenPersistTwice_AndObserve_Error() {

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

    @Test
    public void test_PersistMultipleTimesWithNoPersistenceContextClear_AndObserve_OnlyOneInsertIsTriggered() {

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName("name");

        // persist once
        em.persist(entity1);
        // persist again
        em.persist(entity1);
        // can even flush
        em.flush();
        // persist again
        em.persist(entity1);

        flushAndClear();

        // verify persist
        Entity entity2 = em.find(Entity.class, entity1.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

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

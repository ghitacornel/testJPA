package entities.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityPersist extends TransactionalSetup {

    @Before
    public void before() {
        // verify database state with a native query
        {
            Assert.assertTrue(em.createNativeQuery("select * from SimpleEntity").getResultList().isEmpty());
        }
    }

    @Test
    public void test() {

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
    public void testPersistTwice() {

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");

        // persist once + persistence context flushed and clear + persist twice = error
        em.persist(entity);
        flushAndClear();

        em.persist(entity);
        flushAndClear();

    }

    @Test
    public void testPersistMultipleTimes() {

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName("name");

        // persist multiple times but do not clear persistence context
        em.persist(entity1);
        em.persist(entity1);
        em.flush();

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

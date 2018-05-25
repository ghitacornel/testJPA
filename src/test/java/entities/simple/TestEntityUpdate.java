package entities.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityUpdate extends TransactionalSetup {

    @Before
    public void before() {

        // verify database state with a native query
        {
            Assert.assertTrue(em.createNativeQuery("select * from SimpleEntity").getResultList().isEmpty());
        }

        // create new entity
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");

        // persist
        em.persist(entity);
        flushAndClear();

    }

    @Test
    public void testUpdateExistingEntityUsingMerge() {

        // create new version of existing entity
        Entity newVersionOfExistingEntity = new Entity();
        newVersionOfExistingEntity.setId(1);
        newVersionOfExistingEntity.setName("new name");
        newVersionOfExistingEntity.setValue(12);
        em.merge(newVersionOfExistingEntity);
        flushAndClear();// TODO mandatory check executed queries

        // verify update
        Entity entity = em.find(Entity.class, 1);
        Assert.assertNotNull(entity);
        ReflectionAssert.assertReflectionEquals(newVersionOfExistingEntity, entity);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("new name", objects[1]);
                Assert.assertEquals(12, objects[2]);
            }
        }

    }

    @Test
    public void testUpdateExistingEntityByAlteringAnAlreadyFetchedEntity() {

        // fetch an entity
        Entity originalEntity = em.find(Entity.class, 1);
        Assert.assertNotNull(originalEntity);

        // update
        originalEntity.setName("new name");
        originalEntity.setValue(12);
        flushAndClear();
        // TODO mandatory check executed queries
        // TODO see that merge is not needed

        // verify update
        Entity updatedEntity = em.find(Entity.class, 1);
        Assert.assertNotNull(updatedEntity);
        ReflectionAssert.assertReflectionEquals(originalEntity, updatedEntity);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("new name", objects[1]);
                Assert.assertEquals(12, objects[2]);
            }
        }

    }

}

package entities.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityUpdate extends TransactionalSetup {

    Entity initialEntity;

    @Before
    public void ensureAnExistingEntityIsPresent() {

        verifyCorrespondingTableIsEmpty(Entity.class);

        // create new entity
        initialEntity = new Entity();
        initialEntity.setId(1);
        initialEntity.setName("name");

        persist(initialEntity);
        flushAndClear();

    }

    @Test
    public void testUpdateExistingEntityByAlteringAFetchedEntity() {

        // fetch the entity
        Entity originalEntity = em.find(Entity.class, initialEntity.getId());
        Assert.assertNotNull(originalEntity);

        // update
        originalEntity.setName("new name");
        originalEntity.setValue(12);
        flushAndClear();
        // check executed queries and observe that no other specific entity manager operation operation is needed

        // verify update
        Entity updatedEntity = em.find(Entity.class, initialEntity.getId());
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

    @Test
    public void testUpdateExistingEntityUsingMerge() {

        // create new version of existing entity
        Entity newVersionOfExistingEntity = new Entity();
        newVersionOfExistingEntity.setId(initialEntity.getId());
        newVersionOfExistingEntity.setName("new name");
        newVersionOfExistingEntity.setValue(12);

        // update
        Entity mergedEntity = em.merge(newVersionOfExistingEntity);
        Assert.assertNotSame(mergedEntity, newVersionOfExistingEntity);// observe that a new but managed entity is returned by "merge"
        flushAndClear();// check executed queries

        // verify update
        Entity entity = em.find(Entity.class, initialEntity.getId());
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

    /**
     * MERGE uses a persistence context managed or not managed entity and returns a persistence context managed entity (DB updated or DB inserted)<br>
     * PERSIST uses a persistence context managed or not managed entity and ensure the entity becomes persistence context managed and DB inserted<br>
     */
    @Test
    public void testUpdateExistingEntityUsingMergeAndObserveEffectsOfUpdatesOnMergedAndNotMergedEntities() {

        // create new version of existing entity
        Entity newVersionNotMerged = new Entity();
        newVersionNotMerged.setId(initialEntity.getId());
        newVersionNotMerged.setName("new name");
        newVersionNotMerged.setValue(12);

        // merge first
        Entity newVersionMerged = em.merge(newVersionNotMerged);
        Assert.assertNotSame(newVersionMerged,newVersionNotMerged);

        // merge twice and observe same object is returned even if flush was used, but not clear
        {
            em.flush();
            Entity newVersionMerged2 = em.merge(newVersionMerged);
            Assert.assertSame(newVersionMerged, newVersionMerged2);
        }

        // issue a second update of the not merged new version
        newVersionNotMerged.setName("new name not merged");

        // issue a second update of the merged new version
        newVersionMerged.setName("new name merged");

        flushAndClear();// check executed queries

        // verify update
        Entity entity = em.find(Entity.class, initialEntity.getId());
        Assert.assertNotNull(entity);
        ReflectionAssert.assertReflectionEquals(newVersionMerged, entity);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("new name merged", objects[1]);
                Assert.assertEquals(12, objects[2]);
            }
        }

    }

}

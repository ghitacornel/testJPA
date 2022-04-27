package entities.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityUpdate extends TransactionalSetup {

    Entity initialEntity;

    @BeforeEach
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
        Assertions.assertNotNull(originalEntity);

        // update
        originalEntity.setName("new name");
        originalEntity.setValue(12);
        flushAndClear();
        // check executed queries and observe that no other specific entity manager operation operation is needed

        // verify update
        Entity updatedEntity = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(updatedEntity);
        org.assertj.core.api.Assertions.assertThat(originalEntity).usingRecursiveComparison().isEqualTo(updatedEntity);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("new name", objects[1]);
                Assertions.assertEquals(12, objects[2]);
            }
        }

    }

    // observe UPDATE is issued for all fields even if only 1 was modified
    @Test
    public void testUpdateExistingEntityByAlteringAFetchedEntityAndOnlyASingleField() {

        // fetch the entity
        Entity originalEntity = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(originalEntity);

        // update
        originalEntity.setValue(12);
        flushAndClear();
        // check executed queries and observe that no other specific entity manager operation operation is needed

        // verify update
        Entity updatedEntity = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(updatedEntity);
        org.assertj.core.api.Assertions.assertThat(originalEntity).usingRecursiveComparison().isEqualTo(updatedEntity);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("name", objects[1]);
                Assertions.assertEquals(12, objects[2]);
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
        Assertions.assertNotSame(mergedEntity, newVersionOfExistingEntity);// observe that a new but managed entity is returned by "merge"
        flushAndClear();// check executed queries

        // verify update
        Entity entity = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(entity);
        org.assertj.core.api.Assertions.assertThat(newVersionOfExistingEntity).usingRecursiveComparison().isEqualTo(entity);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("new name", objects[1]);
                Assertions.assertEquals(12, objects[2]);
            }
        }

    }

    /**
     * MERGE uses a persistence context managed or not managed entity and returns a persistence context managed entity (and DB updated or DB inserted)<br>
     * PERSIST uses a persistence context managed or not managed entity and ensure the entity becomes persistence context managed (and DB inserted)<br>
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
        Assertions.assertNotSame(newVersionMerged, newVersionNotMerged);

        // merge twice and observe same object is returned even if flush was used, but not CLEAR
        {
            em.flush();
            Entity newVersionMerged2 = em.merge(newVersionMerged);
            Assertions.assertSame(newVersionMerged, newVersionMerged2);
        }

        // issue a second update of the not merged new version
        newVersionNotMerged.setName("new name not merged");

        // issue a second update of the merged new version
        newVersionMerged.setName("new name merged");

        flushAndClear();// check executed queries

        // verify which update was executed
        Entity entity = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(entity);
        org.assertj.core.api.Assertions.assertThat(newVersionMerged).usingRecursiveComparison().isEqualTo(entity);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("new name merged", objects[1]);
                Assertions.assertEquals(12, objects[2]);
            }
        }

    }

}

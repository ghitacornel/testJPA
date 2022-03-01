package entities.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityCRUD extends TransactionalSetup {

    @BeforeEach
    public void verifyDatabaseState() {
        verifyCorrespondingTableIsEmpty(Entity.class);
    }

    @Test
    public void testCreateReadUpdateReadRemoveRead() {

        // create new entity
        Entity initialEntity = new Entity();
        initialEntity.setId(1);
        initialEntity.setName("name");

        // persist
        em.persist(initialEntity);
        flushAndClear();// mandatory check executed queries

        // verify persist
        Entity entity2 = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(entity2);
        Assertions.assertNotSame(initialEntity, entity2);
        ReflectionAssert.assertReflectionEquals(initialEntity, entity2);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,name,nullableValue from SimpleEntity t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("name", objects[1]);
                Assertions.assertNull(objects[2]);
            }
        }

        // update
        entity2.setName("new name");
        entity2.setValue(12);
        flushAndClear();// mandatory check executed queries

        // verify update
        Entity entity3 = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(entity3);
        Assertions.assertNotSame(entity2, entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

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

        // remove
        Entity toBeRemovedEntity = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(toBeRemovedEntity);
        em.remove(toBeRemovedEntity);
        flushAndClear();// //  mandatory check executed queries

        // verify remove
        Assertions.assertNull(em.find(Entity.class, initialEntity.getId()));

        // verify database state with a native query
        verifyCorrespondingTableIsEmpty(Entity.class);

    }

}

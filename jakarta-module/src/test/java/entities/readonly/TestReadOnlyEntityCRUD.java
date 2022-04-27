package entities.readonly;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.List;

public class TestReadOnlyEntityCRUD extends TransactionalSetup {

    @BeforeEach
    public void verifyDatabaseState() {
        verifyCorrespondingTableIsEmpty(ReadOnlyEntity.class);
    }

    @Test
    public void testCreateReadUpdateReadRemoveRead() {

        // create new entity
        ReadOnlyEntity initialEntity = new ReadOnlyEntity(1, "data");

        // persist
        em.persist(initialEntity);
        flushAndClear();// mandatory check executed queries

        // verify persist
        ReadOnlyEntity entity2 = em.find(ReadOnlyEntity.class, initialEntity.getId());
        Assertions.assertNotNull(entity2);
        Assertions.assertNotSame(initialEntity, entity2);
        org.assertj.core.api.Assertions.assertThat(initialEntity).usingRecursiveComparison().isEqualTo(entity2);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,data from ReadOnlyEntity t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("data", objects[1]);
            }
        }


        // remove
        ReadOnlyEntity toBeRemovedEntity = em.find(ReadOnlyEntity.class, initialEntity.getId());
        Assertions.assertNotNull(toBeRemovedEntity);
        em.remove(toBeRemovedEntity);
        flushAndClear();// //  mandatory check executed queries

        // verify remove
        Assertions.assertNull(em.find(ReadOnlyEntity.class, initialEntity.getId()));

        // verify database state with a native query
        verifyCorrespondingTableIsEmpty(ReadOnlyEntity.class);

    }

}

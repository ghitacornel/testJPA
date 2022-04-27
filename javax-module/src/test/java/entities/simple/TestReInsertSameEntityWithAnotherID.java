package entities.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestReInsertSameEntityWithAnotherID extends TransactionalSetup {

    @BeforeEach
    public void verifyDatabaseState() {
        verifyCorrespondingTableIsEmpty(Entity.class);
    }

    @Test
    public void testReuseEntity() {

        // create new entity
        Entity entity = new Entity();
        entity.setName("name");

        // persist with id 1
        entity.setId(1);
        em.persist(entity);
        flushAndClear();

        // detach
        em.detach(entity);
        flushAndClear();

        // try to persist it again with id 2
        entity.setId(2);
        em.persist(entity);
        flushAndClear();

        // verify 2
        Entity persisted2 = em.find(Entity.class, 2);
        Assertions.assertNotNull(persisted2);
        org.assertj.core.api.Assertions.assertThat(entity).usingRecursiveComparison().isEqualTo(persisted2);

        // verify 1
        Entity persisted1 = em.find(Entity.class, 1);
        Assertions.assertNotNull(persisted1);
        {// adjust the model to reflect expected changes
            entity.setId(1);// set the old id prior to checking
        }
        org.assertj.core.api.Assertions.assertThat(entity).usingRecursiveComparison().isEqualTo(persisted1);

    }

}

package entities.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestEntityDelete extends TransactionalSetup {

    Entity initialEntity;

    @BeforeEach
    public void ensureAnExistingEntityIsPresent() {

        verifyCorrespondingTableIsEmpty(Entity.class);

        // create new entity
        initialEntity = new Entity();
        initialEntity.setId(1);
        initialEntity.setName("name");

        // persist
        em.persist(initialEntity);
        flushAndClear();

    }

    @Test
    public void test() {

        // remove
        Entity toBeRemovedEntity = em.find(Entity.class, initialEntity.getId());
        Assertions.assertNotNull(toBeRemovedEntity);
        em.remove(toBeRemovedEntity);
        flushAndClear();// mandatory check executed queries

        // verify remove
        Assertions.assertNull(em.find(Entity.class, initialEntity.getId()));

        verifyCorrespondingTableIsEmpty(Entity.class);

    }

}

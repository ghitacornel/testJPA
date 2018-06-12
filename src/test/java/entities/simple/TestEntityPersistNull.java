package entities.simple;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestEntityPersistNull extends TransactionalSetup {

    @Before
    public void verifyDatabaseState() {
        verifyCorrespondingTableIsEmpty(Entity.class);
    }

    @Test
    public void testPersist() {

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName(null);

        // persist
        em.persist(entity1);
        flushAndClear();

    }

}

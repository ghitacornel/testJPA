package entities.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestEntityListener extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithListener.class);
    }

    @Test
    public void testListenerIsInvoked() {

        // create new entity
        EntityWithListener entity1 = new EntityWithListener();
        entity1.setId(1);
        entity1.setName("name");

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify
        EntityWithListener entity2 = em.find(EntityWithListener.class, 1);
        Assertions.assertNotNull(entity2);
        Assertions.assertEquals(entity2.getId(), entity1.getId());
        Assertions.assertEquals(entity2.getName(), entity1.getName());

        // verify listener filled fields
        Assertions.assertEquals("prePersist", entity2.getPrePersist());
        Assertions.assertNull(entity2.getPreUpdate());

        // update the entity
        entity2.setName("new name");
        em.merge(entity2);
        flushAndClear();

        // verify
        EntityWithListener entity3 = em.find(EntityWithListener.class, 1);
        Assertions.assertNotNull(entity3);
        Assertions.assertEquals(entity3.getId(), entity1.getId());
        Assertions.assertEquals(entity3.getName(), entity2.getName());

        // verify listener filled fields
        Assertions.assertEquals("prePersist", entity3.getPrePersist());
        Assertions.assertEquals("preUpdate", entity3.getPreUpdate());

    }

}

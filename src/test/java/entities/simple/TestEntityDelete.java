package entities.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestEntityDelete extends TransactionalSetup {

    Entity initialEntity;

    @Before
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
        Entity entity = em.find(Entity.class, initialEntity.getId());
        Assert.assertNotNull(entity);
        em.remove(entity);
        flushAndClear();// mandatory check executed queries

        // verify remove
        Assert.assertNull(em.find(Entity.class, initialEntity.getId()));

        verifyCorrespondingTableIsEmpty(Entity.class);

    }

}

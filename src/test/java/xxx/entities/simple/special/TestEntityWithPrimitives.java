package xxx.entities.simple.special;

import entities.simple.special.EntityWithPrimitives;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestEntityWithPrimitives extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from EntityWithPrimitives t").getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entity
        EntityWithPrimitives entity1 = new EntityWithPrimitives();
        entity1.setId(1);

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify defaults are persisted as expected
        EntityWithPrimitives entity2 = em.find(EntityWithPrimitives.class, 1);
        Assert.assertNotNull(entity2);
        Assert.assertFalse(entity2.isaBoolean());
        Assert.assertEquals(3, entity2.getAnInt());

    }
}


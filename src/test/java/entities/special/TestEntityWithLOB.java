package entities.special;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestEntityWithLOB extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithLOB.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithLOB entity1 = new EntityWithLOB();
        entity1.setId(1);
        entity1.setFileContent(new byte[]{1, 2, 3});
        entity1.setLargeText("qwertyuiop");

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify persist
        EntityWithLOB entity2 = em.find(EntityWithLOB.class, 1);
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

        // update
        entity2.setFileContent(new byte[]{4, 5, 6});
        entity2.setLargeText("asdfghjkl");
        flushAndClear();

        // verify update
        EntityWithLOB entity3 = em.find(EntityWithLOB.class, 1);
        Assert.assertNotNull(entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

    }
}

package entities.converters;

import entities.simple.converters.EntityWithAttributeConverters;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.List;

public class TestEntityAttributeConverters extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from EntityWithAttributeConverters t").getResultList().isEmpty());
    }

    @Test
    public void testCRU() {

        // create new entity
        EntityWithAttributeConverters entity1 = new EntityWithAttributeConverters();
        entity1.setId(1);
        entity1.setBooleanValue(true);
        entity1.setPassword("secret1");

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,booleanValue,password from EntityWithAttributeConverters t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("Y", objects[1]);

                // text must match since we use a fixed algorithm and private key
                Assert.assertEquals("TvZEM7lqd+QNaJKAz5/Gkw==", objects[2]);

            }
        }

        // verify
        EntityWithAttributeConverters entity2 = em.find(EntityWithAttributeConverters.class, entity1.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

        // update
        entity2.setBooleanValue(false);
        entity2.setPassword("secret2");
        em.merge(entity2);
        flushAndClear();

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,booleanValue,password from EntityWithAttributeConverters t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals("N", objects[1]);

                // text must match since we use a fixed algorithm and private key
                Assert.assertEquals("xDhSabqx5D/k0xImrZxPEg==", objects[2]);

            }
        }

        // verify
        EntityWithAttributeConverters entity3 = em.find(EntityWithAttributeConverters.class, entity1.getId());
        Assert.assertNotNull(entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

    }

}

package entities.special;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityWithEnums extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithEnums.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithEnums entity1 = new EntityWithEnums();
        entity1.setId(1);
        entity1.setEnum1(SimpleEnum.ONE);
        entity1.setEnum2(SimpleEnum.TWO);

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify persist
        EntityWithEnums entity2 = em.find(EntityWithEnums.class, 1);
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,enum1,enum2 from EntityWithEnums t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals(SimpleEnum.ONE.name(), objects[1]);
                Assert.assertEquals(SimpleEnum.TWO.ordinal(), objects[2]);
            }
        }

        // update
        entity2.setEnum1(SimpleEnum.THREE);
        entity2.setEnum2(SimpleEnum.FOUR);
        flushAndClear();

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,enum1,enum2 from EntityWithEnums t").getResultList();
            Assert.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assert.assertEquals(1, objects[0]);
                Assert.assertEquals(SimpleEnum.THREE.name(), objects[1]);
                Assert.assertEquals(SimpleEnum.FOUR.ordinal(), objects[2]);
            }
        }

        // verify update
        EntityWithEnums entity3 = em.find(EntityWithEnums.class, 1);
        Assert.assertNotNull(entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

    }

    @Test
    public void testFindByEnum() {

        EntityWithEnums entity1 = new EntityWithEnums();
        {
            entity1.setId(1);
            entity1.setEnum1(SimpleEnum.ONE);
            entity1.setEnum2(SimpleEnum.TWO);
            em.persist(entity1);
            flushAndClear();
        }

        {
            EntityWithEnums entity2 = new EntityWithEnums();
            entity2.setId(2);
            entity2.setEnum1(SimpleEnum.THREE);
            entity2.setEnum2(SimpleEnum.FOUR);
            em.persist(entity2);
            flushAndClear();
        }

        ReflectionAssert.assertReflectionEquals(entity1, em.createQuery("select t from EntityWithEnums t where t.enum1 = entities.special.SimpleEnum.ONE").getSingleResult());
    }
}

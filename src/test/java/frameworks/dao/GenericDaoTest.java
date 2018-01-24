package frameworks.dao;

import entities.simple.Entity;
import entities.simple.SimpleEnum;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsBlockJUnit4ClassRunner;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.reflectionassert.ReflectionAssert;

@RunWith(UnitilsBlockJUnit4ClassRunner.class)
public class GenericDaoTest extends TransactionalSetup {

    @TestedObject
    EntityGenericDao dao = new EntityGenericDao();

    @Test
    public void testCRUD() {

        Assert.assertTrue(dao.findAll().isEmpty());

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName("name1");
        entity1.setEnum1(SimpleEnum.ONE);
        entity1.setEnum2(SimpleEnum.TWO);
        entity1.setBooleanValue(true);

        // persist
        dao.persist(entity1);
        flushAndClear();

        // verify
        Entity entity2 = dao.findById(entity1.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

        // update model
        entity2.setName("name2");
        entity2.setEnum1(SimpleEnum.THREE);
        entity2.setEnum2(SimpleEnum.THREE);
        entity2.setBooleanValue(false);

        // update database
        em.merge(entity2);
        flushAndClear();

        // verify
        Entity entity3 = dao.findById(entity2.getId());
        Assert.assertNotNull(entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

        // delete
        dao.remove(entity3);
        flushAndClear();

        // verify
        Assert.assertTrue(dao.findAll().isEmpty());

    }

    @Test
    public void testRemoveById() {

        Assert.assertTrue(dao.findAll().isEmpty());

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName("name1");
        entity1.setEnum1(SimpleEnum.ONE);
        entity1.setEnum2(SimpleEnum.TWO);
        entity1.setBooleanValue(true);

        // persist
        dao.persist(entity1);
        flushAndClear();

        // verify
        Entity entity2 = dao.findById(entity1.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

        // delete
        dao.removeById(entity2.getId());
        flushAndClear();

        // verify
        Assert.assertTrue(dao.findAll().isEmpty());

    }

    @Test(expected = RuntimeException.class)
    public void testGet() {

        Assert.assertTrue(dao.findAll().isEmpty());

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName("name1");
        entity1.setEnum1(SimpleEnum.ONE);
        entity1.setEnum2(SimpleEnum.TWO);
        entity1.setBooleanValue(true);

        // persist
        dao.persist(entity1);
        flushAndClear();

        // verify
        Entity entity2 = dao.get(entity1.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

        // delete
        dao.removeById(entity2.getId());
        flushAndClear();

        // verify
        try {
            dao.get(entity2.getId());
        } catch (Exception e) {
            Assert.assertEquals("No instance of type " + Entity.class.getCanonicalName() + " found for id " + entity2.getId(), e.getMessage());
            throw e;
        }

        Assert.fail("expect an exception");

    }
}

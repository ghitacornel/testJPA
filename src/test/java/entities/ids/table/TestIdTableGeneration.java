package entities.ids.table;

import entities.ids.table.EntityAWithIdGeneratedFromTable;
import entities.ids.table.EntityBWithIdGeneratedFromTable;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.List;

/**
 * execute it more than 1 time and check the table sequence generator
 *
 * @author Cornel
 */
public class TestIdTableGeneration extends TransactionalSetup {

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from EntityAWithIdGeneratedFromTable t").getResultList().isEmpty());
        Assert.assertTrue(em.createQuery("select t from EntityBWithIdGeneratedFromTable t").getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entities
        EntityAWithIdGeneratedFromTable entityA = new EntityAWithIdGeneratedFromTable();
        Assert.assertNull(entityA.getId());

        EntityBWithIdGeneratedFromTable entityB = new EntityBWithIdGeneratedFromTable();
        Assert.assertNull(entityB.getId());

        // persist
        em.persist(entityA);
        em.persist(entityB);

        // mandatory clear cache (entity managers act as caches also)
        flushAndClear();

        // verify
        List<EntityAWithIdGeneratedFromTable> listA = em.createQuery("select t from EntityAWithIdGeneratedFromTable t", EntityAWithIdGeneratedFromTable.class).getResultList();
        Assert.assertEquals(1, listA.size());
        Assert.assertNotNull(entityA.getId());
        ReflectionAssert.assertReflectionEquals(listA.get(0), entityA);

        List<EntityBWithIdGeneratedFromTable> listB = em.createQuery("select t from EntityBWithIdGeneratedFromTable t", EntityBWithIdGeneratedFromTable.class).getResultList();
        Assert.assertEquals(1, listB.size());
        Assert.assertNotNull(entityB.getId());
        ReflectionAssert.assertReflectionEquals(listB.get(0), entityB);

    }

}

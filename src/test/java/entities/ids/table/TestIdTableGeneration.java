package entities.ids.table;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

/**
 * execute it more than 1 time and check the table sequence generator
 *
 * @author Cornel
 */
public class TestIdTableGeneration extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityAWithIdGeneratedFromTable.class);
        verifyCorrespondingTableIsEmpty(EntityBWithIdGeneratedFromTable.class);
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

        // verify exactly 1 object was persisted
        List<EntityAWithIdGeneratedFromTable> listA = em.createQuery("select t from EntityAWithIdGeneratedFromTable t", EntityAWithIdGeneratedFromTable.class).getResultList();
        Assert.assertEquals(1, listA.size());
        Assert.assertNotNull(entityA.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(listA.get(0), entityA);

        // verify exactly 1 object was persisted
        List<EntityBWithIdGeneratedFromTable> listB = em.createQuery("select t from EntityBWithIdGeneratedFromTable t", EntityBWithIdGeneratedFromTable.class).getResultList();
        Assert.assertEquals(1, listB.size());
        Assert.assertNotNull(entityB.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(listB.get(0), entityB);

    }

}

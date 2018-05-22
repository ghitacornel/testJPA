package xxx.entities.ids.table;

import entities.ids.table.EntityIdA;
import entities.ids.table.EntityIdB;
import main.tests.TransactionalSetup;
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
        Assert.assertTrue(em.createQuery("select t from EntityIdA t").getResultList().isEmpty());
        Assert.assertTrue(em.createQuery("select t from EntityIdB t").getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entities
        EntityIdA entityA = new EntityIdA();
        Assert.assertNull(entityA.getId());
        Assert.assertNull(entityA.getName());
        entityA.setName(entityA.toString());

        EntityIdB entityB = new EntityIdB();
        Assert.assertNull(entityB.getId());
        Assert.assertNull(entityB.getName());
        entityB.setName(entityB.toString());

        // persist
        em.persist(entityA);
        em.persist(entityB);

        // mandatory clear cache (entity managers act as caches also)
        flushAndClear();

        // verify
        List<EntityIdA> listA = em.createQuery("select t from EntityIdA t", EntityIdA.class).getResultList();
        Assert.assertEquals(1, listA.size());
        Assert.assertNotNull(entityA.getId());
        ReflectionAssert.assertReflectionEquals(listA.get(0), entityA);

        List<EntityIdB> listB = em.createQuery("select t from EntityIdB t", EntityIdB.class).getResultList();
        Assert.assertEquals(1, listB.size());
        Assert.assertNotNull(entityB.getId());
        ReflectionAssert.assertReflectionEquals(listB.get(0), entityB);

    }

}

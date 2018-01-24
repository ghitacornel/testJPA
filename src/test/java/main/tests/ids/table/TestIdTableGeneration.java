package main.tests.ids.table;

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
        Assert.assertTrue(em.createQuery("select t from EntityIdA t")
                .getResultList().isEmpty());
        Assert.assertTrue(em.createQuery("select t from EntityIdB t")
                .getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entities
        EntityIdA entitateA = new EntityIdA();
        Assert.assertNull(entitateA.getId());
        Assert.assertNull(entitateA.getName());
        entitateA.setName(entitateA.toString());

        EntityIdB entitateB = new EntityIdB();
        Assert.assertNull(entitateB.getId());
        Assert.assertNull(entitateB.getName());
        entitateB.setName(entitateB.toString());

        // persist
        em.persist(entitateA);
        em.persist(entitateB);

        // mandatory clear cache (entity managers act as caches also)
        flushAndClear();

        // verify
        List<EntityIdA> listA = em.createQuery("select t from EntityIdA t", EntityIdA.class).getResultList();
        Assert.assertEquals(1, listA.size());
        ReflectionAssert.assertReflectionEquals(listA.get(0), entitateA);

        List<EntityIdB> listB = em.createQuery("select t from EntityIdB t", EntityIdB.class).getResultList();
        Assert.assertEquals(1, listB.size());
        ReflectionAssert.assertReflectionEquals(listB.get(0), entitateB);

    }

}

package entities.ids.table;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

/**
 * execute it more than 1 time and check the table sequence generator
 *
 * @author Cornel
 */
public class TestIdTableGeneration extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityAWithIdGeneratedFromTable.class);
        verifyCorrespondingTableIsEmpty(EntityBWithIdGeneratedFromTable.class);
    }

    @Test
    public void test() {

        // create new entities
        EntityAWithIdGeneratedFromTable entityA = new EntityAWithIdGeneratedFromTable();
        Assertions.assertNull(entityA.getId());

        EntityBWithIdGeneratedFromTable entityB = new EntityBWithIdGeneratedFromTable();
        Assertions.assertNull(entityB.getId());

        // persist
        em.persist(entityA);
        em.persist(entityB);

        // mandatory clear cache (entity managers act as caches also)
        flushAndClear();

        // verify exactly 1 object was persisted
        List<EntityAWithIdGeneratedFromTable> listA = em.createQuery("select t from EntityAWithIdGeneratedFromTable t", EntityAWithIdGeneratedFromTable.class).getResultList();
        Assertions.assertEquals(1, listA.size());
        Assertions.assertNotNull(entityA.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(listA.get(0), entityA);

        // verify exactly 1 object was persisted
        List<EntityBWithIdGeneratedFromTable> listB = em.createQuery("select t from EntityBWithIdGeneratedFromTable t", EntityBWithIdGeneratedFromTable.class).getResultList();
        Assertions.assertEquals(1, listB.size());
        Assertions.assertNotNull(entityB.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(listB.get(0), entityB);

    }

}

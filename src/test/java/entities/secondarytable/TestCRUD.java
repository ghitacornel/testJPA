package entities.secondarytable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestCRUD extends TransactionalSetup {

    @BeforeEach
    public void verifyDatabaseState() {
        verifyTableIsEmpty("EMPLOYEE");
        verifyTableIsEmpty("EMPLOYEE_DETAILS");
    }

    @Test
    public void testCreateReadUpdateReadRemoveRead() {

        // create new entity
        Employee initialEntity = new Employee();
        initialEntity.setId(1);
        initialEntity.setFirstName("first name");
        initialEntity.setLastName("last name");
        initialEntity.setEmail("email");
        initialEntity.setSalary(1);

        // persist
        em.persist(initialEntity);
        flushAndClear();// mandatory check executed queries

        // verify persist
        Employee entity2 = em.find(Employee.class, initialEntity.getId());
        Assertions.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(initialEntity, entity2);

        // update
        entity2.setFirstName("first name updated");
        entity2.setLastName("last name updated");
        entity2.setEmail("email updated");
        entity2.setSalary(2);
        flushAndClear();// mandatory check executed queries

        // verify update
        Employee entity3 = em.find(Employee.class, initialEntity.getId());
        Assertions.assertNotNull(entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

        // remove
        Employee toBeRemovedEntity = em.find(Employee.class, initialEntity.getId());
        Assertions.assertNotNull(toBeRemovedEntity);
        em.remove(toBeRemovedEntity);
        flushAndClear();// //  mandatory check executed queries

        // verify remove
        Assertions.assertNull(em.find(Employee.class, initialEntity.getId()));

        // verify database state with a native query
        verifyTableIsEmpty("EMPLOYEE");
        verifyTableIsEmpty("EMPLOYEE_DETAILS");

    }

}

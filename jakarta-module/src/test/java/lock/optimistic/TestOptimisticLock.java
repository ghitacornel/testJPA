package lock.optimistic;

import lock.VersionedEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.Setup;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;

public class TestOptimisticLock extends Setup {

    private VersionedEntity model = buildModel();

    /**
     * need to have data persisted in a separate committed transaction
     */
    @BeforeEach
    public void before() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from VersionedEntity").executeUpdate();
        entityManager.persist(model);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * database manual cleanup is required since this test is not transactional
     */
    @AfterEach
    public void after() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from VersionedEntity").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private VersionedEntity buildModel() {
        VersionedEntity entity = new VersionedEntity();
        entity.setId(1);
        entity.setName("name");
        return entity;
    }

    @Test
    public void test() {

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        entityManager1.getTransaction().begin();
        VersionedEntity entity1 = entityManager1.find(VersionedEntity.class, 1);

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.getTransaction().begin();
        VersionedEntity entity2 = entityManager2.find(VersionedEntity.class, 1);

        entity1.setName("newName1");
        entity2.setName("newName2");

        // first transaction will succeed
        entityManager1.merge(entity1);
        entityManager1.getTransaction().commit();
        entityManager1.close();

        // second transaction should fail
        entityManager2.merge(entity2);
        try {
            entityManager2.getTransaction().commit();
        } catch (RollbackException e) {
            if (e.getCause() instanceof OptimisticLockException) {

                Assertions.assertEquals("Error while committing the transaction", e.getMessage());

                // second transaction failed with expected lock exception and error message => test successful
                return;

            } else {
                Assertions.fail("Unexpected cause " + e.getCause());
            }
        } finally {
            entityManager2.close();
        }

        // if this step is reached => no exception was raised => the test failed
        Assertions.fail("No exception was raised");

    }

}

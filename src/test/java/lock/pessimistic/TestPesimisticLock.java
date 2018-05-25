package lock.pessimistic;

import entities.simple.Entity;
import org.junit.*;
import setup.Setup;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;

public class TestPesimisticLock extends Setup {

    private Entity model = buildModel();

    /**
     * need to have data persisted in a separate committed transaction
     */
    @Before
    public void before() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Entity").executeUpdate();
        entityManager.persist(model);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * database manual cleanup is required since this test is not transactional
     */
    @After
    public void after() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Entity").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private Entity buildModel() {
        Entity entity = new Entity();
        entity.setId(1);
        entity.setName("name");
        return entity;
    }

    @Ignore
    @Test
    public void test() {

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        entityManager1.getTransaction().begin();
        entityManager1.find(Entity.class, 1, LockModeType.PESSIMISTIC_WRITE);

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.getTransaction().begin();

        try {
            entityManager2.find(Entity.class, 1, LockModeType.PESSIMISTIC_WRITE);
        } catch (LockTimeoutException e) {

            // this is the expected exception
            return;

        } finally {
            entityManager1.getTransaction().rollback();
            entityManager2.getTransaction().rollback();
            entityManager1.close();
            entityManager2.close();
        }

        // if this step is reached then no exception was raised hence the test
        // failed
        Assert.fail("No exception was raised");

    }

}

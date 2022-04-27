package lock;

import entities.simple.Entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

public class TestUpdatePessimisticLock extends TransactionalSetup {

    private Entity model = buildModel();

    @BeforeEach
    public void before() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Entity").executeUpdate();
        entityManager.persist(model);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterEach
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

    @Disabled
    @Test
    public void test() {

        final EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        entityManager1.getTransaction().begin();
        final Entity entity1 = entityManager1.find(Entity.class, 1, LockModeType.PESSIMISTIC_WRITE);

        final EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.getTransaction().begin();
        final Entity entity2 = entityManager2.find(Entity.class, 1, LockModeType.NONE);
        // TODO use PESSIMISTIC_WRITE here also and see threads blocked by
        // database transaction synchronization

        entity1.setName("newName1");
        entity2.setName("newName2");

        entityManager1.merge(entity1);
        entityManager1.flush();
        entityManager1.getTransaction().commit();
        entityManager1.close();

        entityManager2.merge(entity2);
        entityManager2.flush();
        entityManager2.getTransaction().commit();
        entityManager2.close();
    }

}

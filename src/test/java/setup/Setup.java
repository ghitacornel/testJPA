package setup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.*;
import java.util.Collection;

public abstract class Setup {

    // persistence unit name must match the persistence unit name specified in the persistence.xml file
    private static final String PERSISTENCE_UNIT_NAME = "examplePersistenceUnit";
    protected static EntityManagerFactory entityManagerFactory;

    // for faster tests we don't use @BeforeEachClass / @AfterEachClass to control this factory
    static {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    protected EntityManager em;

    /**
     * ensure entity manager is build before each test
     */
    @BeforeEach
    final public void beforeEachTest() {
        em = entityManagerFactory.createEntityManager();
    }

    /**
     * ensure entity manager is closed after each test
     */
    @AfterEach
    final public void afterEachTest() {
        em.close();
    }

    /**
     * flush and clear the entity manager
     */
    protected void flushAndClear() {
        if (em.getTransaction().isActive()) {
            em.flush();
            em.clear();
        }
    }

    /**
     * persist given objects
     */
    @SuppressWarnings("unchecked")
    protected void persist(Object... objects) {
        for (Object object : objects) {
            if (Collection.class.isAssignableFrom(object.getClass())) {
                Collection<Object> collection = (Collection<Object>) object;
                collection.forEach(this::persist);
            } else {
                em.persist(object);
            }
        }
    }

    /**
     * verify associated table of given entity is empty
     *
     * @param entityClass a JPA entity
     */
    protected void verifyCorrespondingTableIsEmpty(Class<?> entityClass) {
        Entity entity = entityClass.getAnnotation(Entity.class);
        Assertions.assertNotNull(entity, "not a marked JPA entity");
        String tableName = entityClass.getSimpleName();
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            tableName = table.name();
        }
        Assertions.assertTrue(em.createNativeQuery("select * from " + tableName).getResultList().isEmpty());
        flushAndClear();
    }

    /**
     * verify given by name table is empty
     *
     * @param tableName table name
     */
    protected void verifyTableIsEmpty(String tableName) {
        Assertions.assertTrue(em.createNativeQuery("select * from " + tableName).getResultList().isEmpty());
    }

}

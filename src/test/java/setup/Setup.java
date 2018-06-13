package setup;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.unitils.inject.annotation.InjectIntoByType;

import javax.persistence.*;
import java.util.Collection;

public abstract class Setup {

    // persistence unit name must match the persistence unit name specified in the persistence.xml file
    private static final String PERSISTENCE_UNIT_NAME = "examplePersistenceUnit";
    protected static EntityManagerFactory entityManagerFactory;

    // for faster tests we don't use @BeforeClass / @AfterClass control of this factory
    static {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @InjectIntoByType
    protected EntityManager em;

    @Before
    final public void beforeEachTest() {
        em = entityManagerFactory.createEntityManager();
    }

    @After
    final public void afterEachTest() {
        em.close();
    }

    protected void flushAndClear() {
        if (em.getTransaction().isActive()) {
            em.flush();
            em.clear();
        }
    }

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

    protected void verifyCorrespondingTableIsEmpty(Class<?> entityClass) {
        Entity entity = entityClass.getAnnotation(Entity.class);
        Assert.assertNotNull(entity);
        String tableName = entityClass.getSimpleName();
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            tableName = table.name();
        }
        Assert.assertTrue(em.createNativeQuery("select * from " + tableName).getResultList().isEmpty());
        flushAndClear();
    }

    protected void verifyTableIsEmpty(String tableName) {
        Assert.assertTrue(em.createNativeQuery("select * from " + tableName).getResultList().isEmpty());
    }

}

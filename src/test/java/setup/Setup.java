package setup;

import org.junit.After;
import org.junit.Before;
import org.unitils.inject.annotation.InjectIntoByType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;

public abstract class Setup {

    private static final String PERSISTENCE_UNIT_NAME = "examplePersistenceUnit";
    protected static EntityManagerFactory entityManagerFactory;

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

}

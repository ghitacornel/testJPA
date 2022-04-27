package setup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test Abstraction that controls a transaction which is started at the beginning of the test and rolled back afterwards
 */
public abstract class TransactionalSetup extends Setup {

    /**
     * ensure a transaction is started before each test
     */
    @BeforeEach
    final public void beginTransaction() {
        em.getTransaction().begin();
    }

    /**
     * ensure a transaction is rolled back after each test
     */
    @AfterEach
    final public void rollbackTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}

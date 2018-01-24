package main.tests;

import org.junit.After;
import org.junit.Before;

public abstract class TransactionalSetup extends Setup {

    @Before
    final public void beginTransaction() {
        em.getTransaction().begin();
    }

    @After
    final public void rollbackTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}

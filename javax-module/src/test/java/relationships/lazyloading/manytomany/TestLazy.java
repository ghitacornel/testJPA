package relationships.lazyloading.manytomany;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import javax.persistence.Persistence;

public class TestLazy extends TransactionalSetup {

    MTOMLazyM m;
    MTOMLazyN n;

    @BeforeEach
    public void setUp() {

        m = new MTOMLazyM();
        m.setId(1);
        m.setName("m");

        n = new MTOMLazyN();
        n.setId(1);
        n.setName("n");

        m.getListWithNs().add(n);
        n.getListWithMs().add(m);

        persist(m, n);
        flushAndClear();

    }

    @Test
    public void testLazyLoaded() {

        MTOMLazyM existingM = em.find(MTOMLazyM.class, m.getId());
        flushAndClear();
        Assertions.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingM.getListWithNs()));

        MTOMLazyN existingN = em.find(MTOMLazyN.class, n.getId());
        flushAndClear();
        Assertions.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingN.getListWithMs()));

    }

    @Test
    public void testForceEager() {

        MTOMLazyM existingM = em.find(MTOMLazyM.class, m.getId());
        existingM.getListWithNs().size();// force loading
        flushAndClear();

        MTOMLazyN existingN = em.find(MTOMLazyN.class, n.getId());
        existingN.getListWithMs().size();// force loading
        flushAndClear();

        Assertions.assertTrue(Persistence.getPersistenceUtil().isLoaded(existingM.getListWithNs()));
        Assertions.assertTrue(Persistence.getPersistenceUtil().isLoaded(existingN.getListWithMs()));
        Assertions.assertEquals(1, existingM.getListWithNs().size());
        Assertions.assertEquals(1, existingN.getListWithMs().size());

    }

}

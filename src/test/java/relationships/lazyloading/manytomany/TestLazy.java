package relationships.lazyloading.manytomany;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import javax.persistence.Persistence;

public class TestLazy extends TransactionalSetup {

    MTOMLazyM m;
    MTOMLazyN n;

    @Before
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
        Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingM.getListWithNs()));

        MTOMLazyN existingN = em.find(MTOMLazyN.class, n.getId());
        flushAndClear();
        Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingN.getListWithMs()));

    }

    @Test
    public void testForceEager() {

        MTOMLazyM existingM = em.find(MTOMLazyM.class, m.getId());
        existingM.getListWithNs().size();
        flushAndClear();

        MTOMLazyN existingN = em.find(MTOMLazyN.class, n.getId());
        existingN.getListWithMs().size();
        flushAndClear();

        Assert.assertEquals(1, existingM.getListWithNs().size());
        Assert.assertEquals(1, existingN.getListWithMs().size());

    }

}

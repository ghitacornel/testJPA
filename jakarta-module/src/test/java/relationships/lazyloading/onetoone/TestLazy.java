package relationships.lazyloading.onetoone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import jakarta.persistence.Persistence;

public class TestLazy extends TransactionalSetup {

    OTOOA a;
    OTOOB b;

    @BeforeEach
    public void setUp() {

        a = new OTOOA();
        a.setId(1);
        a.setName("a");

        b = new OTOOB();
        b.setId(1);
        b.setName("b");

        a.setB(b);
        b.setA(a);

        persist(a, b);
        flushAndClear();

    }

    @Test
    public void testLazyLoadedIsIgnored() {

        // observe in this case the referenced entity is always loaded regardless of the LAZY flag
        OTOOA existingA = em.find(OTOOA.class, a.getId());
        flushAndClear();

        Assertions.assertTrue(Persistence.getPersistenceUtil().isLoaded(existingA.getB()));

    }

    @Test
    public void testLazyLoadedIsNotIgnored() {

        OTOOB existingB = em.find(OTOOB.class, b.getId());
        flushAndClear();

        Assertions.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingB.getA()));

    }

    @Test
    public void testForceEager() {

        OTOOB existingB = em.find(OTOOB.class, b.getId());
        existingB.getA().getName();// force proxy
        flushAndClear();

        Assertions.assertTrue(Persistence.getPersistenceUtil().isLoaded(existingB.getA()));

    }

    @Test
    public void testForceEagerOnAlreadyLoadedFieldDoesNotTriggerTheForcedLoadingOfTheWholeEntity() {

        OTOOB existingB = em.find(OTOOB.class, a.getId());
        existingB.getA().getId();// try to force proxy on already loaded field
        flushAndClear();

        Assertions.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingB.getA()));

    }

}

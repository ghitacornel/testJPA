package relationships.many.to.many.list.nocascade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemove extends TransactionalSetup {

    M m;
    N n;

    @Before
    public void before() {

        n = new N();
        n.setId(1);
        n.setName("n 1 name");

        m = new M();
        m.setId(1);
        m.setName("m 1 name");

        n.getListWithMs().add(m);
        m.getListWithNs().add(n);

        persist(m);
        persist(n);
        flushAndClear();
    }

    @Test
    public void testRemoveFromTheOwningSide() {

        // remove
        em.remove(em.find(M.class, m.getId()));
        flushAndClear();

        // verify final
        {// adjust model to reflect expected changes
            n.getListWithMs().remove(m);
        }
        Assert.assertNull(em.find(M.class, m.getId()));
        ReflectionAssert.assertReflectionEquals(n, em.find(N.class, n.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testRemoveFromTheNonOwningSide() {

        // remove
        N existingN = em.find(N.class, this.n.getId());
        for (M existingM : existingN.getListWithMs()) {
            existingM.getListWithNs().remove(existingN);
        }
        em.remove(existingN);
        flushAndClear();

        // verify final
        {// adjust model to reflect expected changes
            m.getListWithNs().remove(this.n);
        }
        Assert.assertNull(em.find(N.class, this.n.getId()));
        ReflectionAssert.assertReflectionEquals(m, em.find(M.class, m.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testRemoveFromTheNonOwningSideNotWorking() {

        // remove
        em.remove(em.find(N.class, n.getId()));
        flushAndClear();


    }

}
package relationships.many.to.many.list.nocascade;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestRemoveLink extends TransactionalSetup {

    M m;
    N n;

    @Before
    public void before() {

        m = new M();
        m.setId(1);
        m.setName("m 1 name");

        n = new N();
        n.setId(1);
        n.setName("n 1 name");

        n.getListWithMs().add(m);
        m.getListWithNs().add(n);

        persist(m);
        persist(n);
        flushAndClear();

    }

    @Test
    public void testRemoveLinkFromTheOwningSide() {

        // remove link
        em.find(M.class, m.getId()).getListWithNs().remove(n);
        flushAndClear();

        // validate link not removed
        ReflectionAssert.assertReflectionEquals(m, em.find(M.class, m.getId()));
        ReflectionAssert.assertReflectionEquals(n, em.find(N.class, n.getId()));

    }

    @Test
    public void testRemoveLinkFromTheNonOwning() {

        // remove link
        em.find(N.class, n.getId()).getListWithMs().remove(m);
        flushAndClear();

        // validate link not removed
        ReflectionAssert.assertReflectionEquals(n, em.find(N.class, n.getId()));
        ReflectionAssert.assertReflectionEquals(m, em.find(M.class, m.getId()));

    }

    @Test
    public void testSafeRemoveLink() {

        // safes way to remove link is to remove it from both sides
        em.find(M.class, m.getId()).getListWithNs().remove(n);
        em.find(N.class, n.getId()).getListWithMs().remove(m);
        flushAndClear();

        // validate link removed
        {// adjust model to reflect expected changes
            m.getListWithNs().clear();
            n.getListWithMs().clear();
        }
        ReflectionAssert.assertReflectionEquals(n, em.find(N.class, n.getId()));
        ReflectionAssert.assertReflectionEquals(m, em.find(M.class, m.getId()));

    }

}
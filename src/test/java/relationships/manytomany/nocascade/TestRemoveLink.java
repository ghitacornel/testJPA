package relationships.manytomany.nocascade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestRemoveLink extends TransactionalSetup {

    NoCascadeM m;
    NoCascadeN n;

    @BeforeEach
    public void before() {

        m = new NoCascadeM();
        m.setId(1);
        m.setName("m 1 name");

        n = new NoCascadeN();
        n.setId(1);
        n.setName("n 1 name");

        n.getListWithMs().add(m);
        m.getListWithNs().add(n);

        persist(m);
        persist(n);
        flushAndClear();

    }

    @Test
    public void testRemoveLinkFromTheOwningSideNotWorking() {

        // remove link
        em.find(NoCascadeM.class, m.getId()).getListWithNs().remove(n);
        flushAndClear();

        // validate link not removed
        ReflectionAssert.assertReflectionEquals(m, em.find(NoCascadeM.class, m.getId()));
        ReflectionAssert.assertReflectionEquals(n, em.find(NoCascadeN.class, n.getId()));

    }

    @Test
    public void testRemoveLinkFromTheNonOwningSideNotWorking() {

        // remove link
        em.find(NoCascadeN.class, n.getId()).getListWithMs().remove(m);
        flushAndClear();

        // validate link not removed
        ReflectionAssert.assertReflectionEquals(n, em.find(NoCascadeN.class, n.getId()));
        ReflectionAssert.assertReflectionEquals(m, em.find(NoCascadeM.class, m.getId()));

    }

    @Test
    public void testRemoveLinkFromBothSidesWorks() {

        // safes way to remove link is to remove it from both sides
        NoCascadeM actualM = em.find(NoCascadeM.class, m.getId());
        NoCascadeN actualN = em.find(NoCascadeN.class, n.getId());
        actualM.getListWithNs().remove(actualN);
        actualN.getListWithMs().remove(actualM);
        flushAndClear();

        // validate link removed
        {// adjust model to reflect expected changes
            m.getListWithNs().remove(n);
            n.getListWithMs().remove(m);
        }
        ReflectionAssert.assertReflectionEquals(n, em.find(NoCascadeN.class, n.getId()));
        ReflectionAssert.assertReflectionEquals(m, em.find(NoCascadeM.class, m.getId()));

    }

}
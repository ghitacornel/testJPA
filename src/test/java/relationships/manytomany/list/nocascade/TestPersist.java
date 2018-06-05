package relationships.manytomany.list.nocascade;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestPersist extends TransactionalSetup {

    @Test
    public void testPersist() {

        NoCascadeN n = new NoCascadeN();
        n.setId(1);
        n.setName("n 1 name");

        NoCascadeM m = new NoCascadeM();
        m.setId(1);
        m.setName("m 1 name");

        n.getListWithMs().add(m);
        m.getListWithNs().add(n);

        // must persist both sides
        persist(m);
        persist(n);
        flushAndClear();

        // verify persist
        ReflectionAssert.assertReflectionEquals(m, em.find(NoCascadeM.class, m.getId()));
        ReflectionAssert.assertReflectionEquals(n, em.find(NoCascadeN.class, n.getId()));

    }

}
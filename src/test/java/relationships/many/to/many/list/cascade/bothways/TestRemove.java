package relationships.many.to.many.list.cascade.bothways;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestRemove extends TransactionalSetup {

    CascadeBothWaysM m;
    CascadeBothWaysN n;

    @Before
    public void buildModel() {

        m = new CascadeBothWaysM();
        m.setId(1);
        m.setName("m 1 name");

        n = new CascadeBothWaysN();
        n.setId(1);
        n.setName("n 1 name");

        n.getListWithMs().add(m);
        m.getListWithNs().add(n);

    }

    @Before
    public void before() {
        persist(m);
        flushAndClear();
    }


    @Test
    public void testRemoveFromTheOwningSideCascades() {

        // remove
        em.remove(em.find(CascadeBothWaysM.class, m.getId()));
        flushAndClear();

        // verify final
        Assert.assertNull(em.find(CascadeBothWaysM.class, m.getId()));
        Assert.assertNull(em.find(CascadeBothWaysN.class, n.getId()));

    }

    @Test
    public void testRemoveFromTheNonOwningSideCascades() {

        // remove
        em.remove(em.find(CascadeBothWaysN.class, n.getId()));
        flushAndClear();

        // verify final
        Assert.assertNull(em.find(CascadeBothWaysN.class, n.getId()));
        Assert.assertNull(em.find(CascadeBothWaysM.class, m.getId()));

    }

}
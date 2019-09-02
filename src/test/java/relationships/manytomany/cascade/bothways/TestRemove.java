package relationships.manytomany.cascade.bothways;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestRemove extends TransactionalSetup {

    CascadeBothWaysM m;
    CascadeBothWaysN n1;
    CascadeBothWaysN n2;

    @Before
    public void buildModel() {

        m = new CascadeBothWaysM();
        m.setId(1);
        m.setName("m 1 name");

        n1 = new CascadeBothWaysN();
        n1.setId(1);
        n1.setName("n 1 name");

        n2 = new CascadeBothWaysN();
        n2.setId(2);
        n2.setName("n 2 name");

        n1.getListWithMs().add(m);
        n2.getListWithMs().add(m);
        m.getListWithNs().add(n1);
        m.getListWithNs().add(n2);

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

        // verify cascade removal was propagated
        Assert.assertNull(em.find(CascadeBothWaysM.class, m.getId()));
        Assert.assertNull(em.find(CascadeBothWaysN.class, n1.getId()));
        Assert.assertNull(em.find(CascadeBothWaysN.class, n2.getId()));

    }

    @Test
    public void testRemoveFromTheNonOwningSideCascades() {

        // remove
        em.remove(em.find(CascadeBothWaysN.class, n1.getId()));
        flushAndClear();

        // verify cascade removal was propagated
        Assert.assertNull(em.find(CascadeBothWaysN.class, n1.getId()));
        Assert.assertNull(em.find(CascadeBothWaysN.class, n2.getId()));
        Assert.assertNull(em.find(CascadeBothWaysM.class, m.getId()));

    }

}
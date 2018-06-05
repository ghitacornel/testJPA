package relationships.manytomany.list.cascade.bothways;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestRemoveCascadedDeletesAll extends TransactionalSetup {

    CascadeBothWaysM m1;
    CascadeBothWaysN n1;
    CascadeBothWaysN n2;

    @Before
    public void buildModel() {

        m1 = new CascadeBothWaysM();
        m1.setId(1);
        m1.setName("m 1 name");

        n1 = new CascadeBothWaysN();
        n1.setId(1);
        n1.setName("n 1 name");

        n2 = new CascadeBothWaysN();
        n2.setId(2);
        n2.setName("n 2 name");

        n1.getListWithMs().add(m1);
        n2.getListWithMs().add(m1);
        m1.getListWithNs().add(n1);
        m1.getListWithNs().add(n2);

    }

    @Before
    public void before() {
        persist(m1);
        flushAndClear();
    }

    @Test
    public void testRemoveFromTheOwningSide() {

        // remove
        em.remove(em.find(CascadeBothWaysM.class, m1.getId()));
        flushAndClear();


        // verify removal
        Assert.assertNull(em.find(CascadeBothWaysM.class, m1.getId()));
        Assert.assertNull(em.find(CascadeBothWaysN.class, n1.getId()));
        Assert.assertNull(em.find(CascadeBothWaysN.class, n2.getId()));

    }

    @Test
    public void testRemoveFromTheNonOwningSide() {

        // remove
        em.remove(em.find(CascadeBothWaysN.class, n1.getId()));
        flushAndClear();


        // verify removal
        Assert.assertNull(em.find(CascadeBothWaysM.class, m1.getId()));
        Assert.assertNull(em.find(CascadeBothWaysN.class, n1.getId()));
        Assert.assertNull(em.find(CascadeBothWaysN.class, n2.getId()));

    }

}
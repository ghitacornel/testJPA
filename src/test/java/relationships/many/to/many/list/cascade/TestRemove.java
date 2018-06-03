package relationships.many.to.many.list.cascade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemove extends TransactionalSetup {

    CascadeM m1;
    CascadeN n1;

    @Before
    public void buildModel() {

        m1 = new CascadeM();
        m1.setId(1);
        m1.setName("m 1 name");

        n1 = new CascadeN();
        n1.setId(1);
        n1.setName("n 1 name");

        n1.getListWithMs().add(m1);
        m1.getListWithNs().add(n1);

    }

    @Before
    public void before() {
        persist(m1);
        flushAndClear();
    }

    @Test
    public void testRemoveFromTheOwningSide() {

        // remove
        em.remove(em.find(CascadeM.class, m1.getId()));
        flushAndClear();


        // verify removal
        Assert.assertNull(em.find(CascadeM.class, m1.getId()));
        Assert.assertNull(em.find(CascadeN.class, n1.getId()));

    }

    @Test
    public void testRemoveFromTheNonOwningSide() {

        // remove
        em.remove(em.find(CascadeN.class, n1.getId()));
        flushAndClear();


        // verify removal
        Assert.assertNull(em.find(CascadeM.class, m1.getId()));
        Assert.assertNull(em.find(CascadeN.class, n1.getId()));

    }

    @Test
    public void testRemoveOfLinkFromTheOwningSide() {

        // remove
        em.find(CascadeM.class, m1.getId()).getListWithNs().clear();
        flushAndClear();


        // verify removal of link only
        {// adjust model to reflect the expected result
            m1.getListWithNs().clear();
            n1.getListWithMs().clear();
        }
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testRemoveOfLinkFromTheNonOwningSide() {

        // remove
        em.find(CascadeM.class, n1.getId()).getListWithNs().clear();
        flushAndClear();


        // verify removal of link only
        {// adjust model to reflect the expected result
            m1.getListWithNs().clear();
            n1.getListWithMs().clear();
        }
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
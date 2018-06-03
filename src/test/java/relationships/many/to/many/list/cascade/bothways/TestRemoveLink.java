package relationships.many.to.many.list.cascade.bothways;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemoveLink extends TransactionalSetup {

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
    public void testRemoveOfLinkFromTheOwningSide() {

        // remove
        em.find(CascadeBothWaysM.class, m.getId()).getListWithNs().clear();
        flushAndClear();


        // verify removal of link only
        {// adjust model to reflect the expected result
            m.getListWithNs().clear();
            n.getListWithMs().clear();
        }
        ReflectionAssert.assertReflectionEquals(m, em.find(CascadeBothWaysM.class, m.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n, em.find(CascadeBothWaysN.class, n.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testRemoveOfLinkFromTheNonOwningSide() {

        // remove
        em.find(CascadeBothWaysM.class, n.getId()).getListWithNs().clear();
        flushAndClear();


        // verify removal of link only
        {// adjust model to reflect the expected result
            m.getListWithNs().clear();
            n.getListWithMs().clear();
        }
        ReflectionAssert.assertReflectionEquals(m, em.find(CascadeBothWaysM.class, m.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n, em.find(CascadeBothWaysN.class, n.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
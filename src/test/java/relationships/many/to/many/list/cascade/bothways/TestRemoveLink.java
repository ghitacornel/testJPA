package relationships.many.to.many.list.cascade.bothways;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestRemoveLink extends TransactionalSetup {

    CascadeBothWaysM m1;
    CascadeBothWaysN n1;

    @Before
    public void buildModel() {

        m1 = new CascadeBothWaysM();
        m1.setId(1);
        m1.setName("m 1 name");

        n1 = new CascadeBothWaysN();
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
    public void testRemoveOfLinkFromTheOwningSide() {

        // remove
        em.find(CascadeBothWaysM.class, m1.getId()).getListWithNs().clear();
        flushAndClear();


        // verify removal of link only
        {// adjust model to reflect the expected result
            m1.getListWithNs().clear();
            n1.getListWithMs().clear();
        }
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeBothWaysM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeBothWaysN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testRemoveOfLinkFromTheNonOwningSide() {

        // remove
        em.find(CascadeBothWaysM.class, n1.getId()).getListWithNs().clear();
        flushAndClear();


        // verify removal of link only
        {// adjust model to reflect the expected result
            m1.getListWithNs().clear();
            n1.getListWithMs().clear();
        }
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeBothWaysM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeBothWaysN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
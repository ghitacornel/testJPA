package relationships.manytomany.cascade.bothways;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestPersist extends TransactionalSetup {

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

    @Test
    public void testPersistFromTheOwningSide() {

        // persist and expect cascade
        em.persist(m1);
        flushAndClear();

        // verify
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeBothWaysM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeBothWaysN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n2, em.find(CascadeBothWaysN.class, n2.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testPersistFromTheNonOwningSide() {

        // persist and expect cascade
        em.persist(n1);
        flushAndClear();

        // verify
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeBothWaysM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeBothWaysN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n2, em.find(CascadeBothWaysN.class, n2.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
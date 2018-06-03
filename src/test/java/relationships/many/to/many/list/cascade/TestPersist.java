package relationships.many.to.many.list.cascade;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestPersist extends TransactionalSetup {

    CascadeM m1;
    CascadeN n1;
    CascadeN n2;

    @Before
    public void buildModel() {

        m1 = new CascadeM();
        m1.setId(1);
        m1.setName("m 1 name");

        n1 = new CascadeN();
        n1.setId(1);
        n1.setName("n 1 name");

        n2 = new CascadeN();
        n2.setId(2);
        n2.setName("n 2 name");

        n1.getListWithMs().add(m1);
        n2.getListWithMs().add(m1);
        m1.getListWithNs().add(n1);
        m1.getListWithNs().add(n2);

    }

    @Test
    public void testPersistFromTheOwningSide() {

        // persist
        em.persist(m1);
        flushAndClear();

        // verify
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeM.class, 1), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeN.class, 1), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n2, em.find(CascadeN.class, 2), ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testPersistFromTheNonOwningSide() {

        // persist
        em.persist(n1);
        flushAndClear();

        // verify
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeM.class, 1), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeN.class, 1), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n2, em.find(CascadeN.class, 2), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
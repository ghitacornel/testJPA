package relationships.manytomany.cascade.oneside;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

/**
 * only 1 test is enough to prove the changes are propagated one way only
 */
public class TestClearOwningSideChildrenList extends TransactionalSetup {

    CascadeOneSideM m1;
    CascadeOneSideN n1;
    CascadeOneSideN n2;

    @Before
    public void buildModel() {

        m1 = new CascadeOneSideM();
        m1.setId(1);
        m1.setName("m 1 name");

        n1 = new CascadeOneSideN();
        n1.setId(1);
        n1.setName("n 1 name");

        n2 = new CascadeOneSideN();
        n2.setId(2);
        n2.setName("n 2 name");

        n1.getListWithMs().add(m1);
        n2.getListWithMs().add(m1);
        m1.getListWithNs().add(n1);
        m1.getListWithNs().add(n2);

    }

    @Test
    public void testClearListOfChildrenFromTheOwningSide() {

        // persist and expect cascade
        em.persist(m1);
        flushAndClear();

        // verify all is persisted
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeOneSideM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeOneSideN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n2, em.find(CascadeOneSideN.class, n2.getId()), ReflectionComparatorMode.LENIENT_ORDER);

        // get parent and clear list of children
        em.find(CascadeOneSideM.class, m1.getId()).getListWithNs().clear();
        flushAndClear();

        // verify links are cleared
        m1.getListWithNs().clear();
        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeOneSideM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        n1.getListWithMs().clear();
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeOneSideN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        n2.getListWithMs().clear();
        ReflectionAssert.assertReflectionEquals(n2, em.find(CascadeOneSideN.class, n2.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }


}
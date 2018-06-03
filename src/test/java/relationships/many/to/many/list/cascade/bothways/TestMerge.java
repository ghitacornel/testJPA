package relationships.many.to.many.list.cascade.bothways;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestMerge extends TransactionalSetup {

    CascadeBothWaysM m1;
    CascadeBothWaysN n1;
    CascadeBothWaysN nUnaffected;

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

        {// this is tested altogether via list reflections
            nUnaffected = new CascadeBothWaysN();
            nUnaffected.setId(111);
            nUnaffected.setName("n 1 name");
            m1.getListWithNs().add(nUnaffected);
            nUnaffected.getListWithMs().add(m1);
        }

        em.persist(m1);
        flushAndClear();

    }

    @Test
    public void testMerge() {


        CascadeBothWaysM m1Updated = new CascadeBothWaysM();
        m1Updated.setId(1);
        m1Updated.setName("m 1 new name");

        CascadeBothWaysN n1Updated = new CascadeBothWaysN();
        n1Updated.setId(1);
        n1Updated.setName("n 1 new name");

        CascadeBothWaysN n2New = new CascadeBothWaysN();
        n2New.setId(2);
        n2New.setName("n 2 new name");

        // ensure links are set
        m1Updated.getListWithNs().add(n1Updated);
        m1Updated.getListWithNs().add(n2New);
        n1Updated.getListWithMs().add(m1Updated);
        n2New.getListWithMs().add(m1Updated);

        // merge and expect cascade
        em.merge(m1Updated);
        flushAndClear();

        // verify
        {// first alter the initial model to match expectations

            m1.setName("m 1 new name");
            m1.getListWithNs().add(n2New);
            m1.getListWithNs().remove(nUnaffected);

            n1.setName("n 1 new name");

            nUnaffected.getListWithMs().clear();

        }

        ReflectionAssert.assertReflectionEquals(m1, em.find(CascadeBothWaysM.class, m1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(CascadeBothWaysN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n2New, em.find(CascadeBothWaysN.class, n2New.getId()), ReflectionComparatorMode.LENIENT_ORDER);

        // the unaffected is not removed, only the links between entities are removed
        ReflectionAssert.assertReflectionEquals(nUnaffected, em.find(CascadeBothWaysN.class, nUnaffected.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
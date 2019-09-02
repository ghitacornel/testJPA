package relationships.manytomany.cascade.bothways;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestMerge extends TransactionalSetup {

    CascadeBothWaysM m;
    CascadeBothWaysN n;
    CascadeBothWaysN nUnaffected;

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

        {// this is tested altogether via strict reflections
            nUnaffected = new CascadeBothWaysN();
            nUnaffected.setId(111);
            nUnaffected.setName("n 1 name");
            m.getListWithNs().add(nUnaffected);
            nUnaffected.getListWithMs().add(m);
        }

        em.persist(m);
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
        {// alter the initial model to match expectations

            m.setName("m 1 new name");
            m.getListWithNs().add(n2New);
            m.getListWithNs().remove(nUnaffected);

            n.setName("n 1 new name");

            nUnaffected.getListWithMs().clear();

        }

        ReflectionAssert.assertReflectionEquals(m, em.find(CascadeBothWaysM.class, m.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n, em.find(CascadeBothWaysN.class, n.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n2New, em.find(CascadeBothWaysN.class, n2New.getId()), ReflectionComparatorMode.LENIENT_ORDER);

        // the unaffected is not removed, only the links between entities are removed
        ReflectionAssert.assertReflectionEquals(nUnaffected, em.find(CascadeBothWaysN.class, nUnaffected.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
package relationships.many.to.many.list;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private List<Object> model = buildModel();

    static List<Object> buildModel() {
        List<Object> objects = new ArrayList<>();

        {

            N n1 = new N();
            n1.setId(1);
            n1.setName("n 1 name");
            objects.add(n1);

            N n2 = new N();
            n2.setId(2);
            n2.setName("n 2 name");
            objects.add(n2);

            N n3 = new N();
            n3.setId(3);
            n3.setName("n 3 name");
            objects.add(n3);

            N n4 = new N();
            n4.setId(4);
            n4.setName("n 4 name");
            objects.add(n4);

            M m1 = new M();
            m1.setId(1);
            m1.setName("m 1 name");
            objects.add(m1);

            M m2 = new M();
            m2.setId(2);
            m2.setName("m 2 name");
            objects.add(m2);

            M m3 = new M();
            m3.setId(3);
            m3.setName("m 3 name");
            objects.add(m3);

            M m4 = new M();
            m4.setId(4);
            m4.setName("m 4 name");
            objects.add(m4);

            n1.getListWithMs().add(m1);
            m1.getListWithNs().add(n1);

            n2.getListWithMs().add(m2);
            n2.getListWithMs().add(m3);
            n3.getListWithMs().add(m2);
            n3.getListWithMs().add(m3);

            m2.getListWithNs().add(n2);
            m2.getListWithNs().add(n3);
            m3.getListWithNs().add(n2);
            m3.getListWithNs().add(n3);

        }

        return objects;
    }

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testM() {

        List<M> list = em.createQuery("select t from M t", M.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(4, 8), list, ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test
    public void testN() {

        List<N> list = em.createQuery("select t from N t", N.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(0, 4), list, ReflectionComparatorMode.LENIENT_ORDER);
    }
}

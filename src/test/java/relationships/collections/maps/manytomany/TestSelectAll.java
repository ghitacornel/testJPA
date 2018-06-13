package relationships.collections.maps.manytomany;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private List<Object> model = buildModel();

    private List<Object> buildModel() {
        List<Object> objects = new ArrayList<>();

        {

            NMap n1 = new NMap();
            n1.setId(1);
            n1.setName("n 1 name");
            objects.add(n1);

            NMap n2 = new NMap();
            n2.setId(2);
            n2.setName("n 2 name");
            objects.add(n2);

            NMap n3 = new NMap();
            n3.setId(3);
            n3.setName("n 3 name");
            objects.add(n3);

            NMap n4 = new NMap();
            n4.setId(4);
            n4.setName("n 4 name");
            objects.add(n4);

            MMap m1 = new MMap();
            m1.setId(1);
            m1.setName("m 1 name");
            objects.add(m1);

            MMap m2 = new MMap();
            m2.setId(2);
            m2.setName("m 2 name");
            objects.add(m2);

            MMap m3 = new MMap();
            m3.setId(3);
            m3.setName("m 3 name");
            objects.add(m3);

            MMap m4 = new MMap();
            m4.setId(4);
            m4.setName("m 4 name");
            objects.add(m4);

            n1.getMapWithNs().put(m1.getId(), m1);
            m1.getMapWithNs().put(n1.getId(), n1);

            n2.getMapWithNs().put(m2.getId(), m2);
            n2.getMapWithNs().put(m3.getId(), m3);
            n3.getMapWithNs().put(m2.getId(), m2);
            n3.getMapWithNs().put(m3.getId(), m3);

            m2.getMapWithNs().put(n2.getId(), n2);
            m2.getMapWithNs().put(n3.getId(), n3);
            m3.getMapWithNs().put(n2.getId(), n2);
            m3.getMapWithNs().put(n3.getId(), n3);

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

        List<MMap> list = em.createQuery("select t from MMap t", MMap.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(4, 8), list, ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test
    public void testN() {

        List<NMap> list = em.createQuery("select t from NMap t", NMap.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(0, 4), list, ReflectionComparatorMode.LENIENT_ORDER);
    }
}

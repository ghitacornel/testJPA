package relationships.collections.sets.manytomany;

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

            NSet n1 = new NSet();
            n1.setId(1);
            n1.setName("n 1 name");
            objects.add(n1);

            NSet n2 = new NSet();
            n2.setId(2);
            n2.setName("n 2 name");
            objects.add(n2);

            NSet n3 = new NSet();
            n3.setId(3);
            n3.setName("n 3 name");
            objects.add(n3);

            NSet n4 = new NSet();
            n4.setId(4);
            n4.setName("n 4 name");
            objects.add(n4);

            MSet m1 = new MSet();
            m1.setId(1);
            m1.setName("m 1 name");
            objects.add(m1);

            MSet m2 = new MSet();
            m2.setId(2);
            m2.setName("m 2 name");
            objects.add(m2);

            MSet m3 = new MSet();
            m3.setId(3);
            m3.setName("m 3 name");
            objects.add(m3);

            MSet m4 = new MSet();
            m4.setId(4);
            m4.setName("m 4 name");
            objects.add(m4);

            n1.getSetWithMs().add( m1);
            m1.getSetWithNs().add( n1);

            n2.getSetWithMs().add( m2);
            n2.getSetWithMs().add( m3);
            n3.getSetWithMs().add( m2);
            n3.getSetWithMs().add( m3);

            m2.getSetWithNs().add( n2);
            m2.getSetWithNs().add( n3);
            m3.getSetWithNs().add( n2);
            m3.getSetWithNs().add( n3);

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

        List<MSet> list = em.createQuery("select t from MSet t", MSet.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(4, 8), list, ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test
    public void testN() {

        List<NSet> list = em.createQuery("select t from NSet t", NSet.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(0, 4), list, ReflectionComparatorMode.LENIENT_ORDER);
    }
}

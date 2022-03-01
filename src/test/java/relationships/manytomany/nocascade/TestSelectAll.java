package relationships.manytomany.nocascade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

            NoCascadeN n1 = new NoCascadeN();
            n1.setId(1);
            n1.setName("n 1 name");
            objects.add(n1);

            NoCascadeN n2 = new NoCascadeN();
            n2.setId(2);
            n2.setName("n 2 name");
            objects.add(n2);

            NoCascadeN n3 = new NoCascadeN();
            n3.setId(3);
            n3.setName("n 3 name");
            objects.add(n3);

            NoCascadeN n4 = new NoCascadeN();
            n4.setId(4);
            n4.setName("n 4 name");
            objects.add(n4);

            NoCascadeM m1 = new NoCascadeM();
            m1.setId(1);
            m1.setName("m 1 name");
            objects.add(m1);

            NoCascadeM m2 = new NoCascadeM();
            m2.setId(2);
            m2.setName("m 2 name");
            objects.add(m2);

            NoCascadeM m3 = new NoCascadeM();
            m3.setId(3);
            m3.setName("m 3 name");
            objects.add(m3);

            NoCascadeM m4 = new NoCascadeM();
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

    @BeforeEach
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testSelectAllM() {
        List<NoCascadeM> list = em.createQuery("select t from NoCascadeM t", NoCascadeM.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(4, 8), list, ReflectionComparatorMode.LENIENT_ORDER);
    }

    @Test
    public void testSelectAllN() {
        List<NoCascadeN> list = em.createQuery("select t from NoCascadeN t", NoCascadeN.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(0, 4), list, ReflectionComparatorMode.LENIENT_ORDER);
    }
}

package main.tests.relationships.many.to.many.list;

import entities.relationships.many.to.many.list.M;
import entities.relationships.many.to.many.list.N;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestRemoveLink extends TransactionalSetup {

    private List<Object> model = buildModel();

    static List<Object> buildModel() {
        List<Object> objects = new ArrayList<>();

        {

            N n1 = new N();
            n1.setId(1);
            n1.setName("n 1 name");
            objects.add(n1);

            M m1 = new M();
            m1.setId(1);
            m1.setName("m 1 name");
            objects.add(m1);

            n1.getListWithMs().add(m1);
            m1.getListWithNs().add(n1);

        }

        return objects;
    }

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testRemoveLink() {

        // verify initial
        {
            List<N> listN = em.createQuery("select t from N t", N.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(0, 1), listN, ReflectionComparatorMode.LENIENT_ORDER);
            List<M> listM = em.createQuery("select t from M t", M.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(1, 2), listM, ReflectionComparatorMode.LENIENT_ORDER);
            flushAndClear();
        }

        // remove link
        {
            N n = em.find(N.class, 1);
            Assert.assertNotNull(n);
            Assert.assertEquals(1, n.getListWithMs().size());

            // XXX remove from both ways otherwise it won't work
            M m = n.getListWithMs().get(0);
            n.getListWithMs().remove(m);
            m.getListWithNs().remove(n);

            flushAndClear();
        }

        // verify final
        {
            List<N> listN = em.createQuery("select t from N t", N.class).getResultList();
            N n = (N) model.get(0);
            n.getListWithMs().clear();
            ReflectionAssert.assertReflectionEquals(model.subList(0, 1), listN, ReflectionComparatorMode.LENIENT_ORDER);
            List<M> listM = em.createQuery("select t from M t", M.class).getResultList();
            M m = (M) model.get(1);
            m.getListWithNs().clear();
            ReflectionAssert.assertReflectionEquals(model.subList(1, 2), listM, ReflectionComparatorMode.LENIENT_ORDER);
            flushAndClear();
        }

        // validate link removed
        {
            N n = em.find(N.class, 1);
            Assert.assertNotNull(n);
            Assert.assertEquals(0, n.getListWithMs().size());
            flushAndClear();
        }

    }


}

package relationships.many.to.many.list;

import entities.relationships.many.to.many.list.M;
import entities.relationships.many.to.many.list.N;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestRemove extends TransactionalSetup {

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
    public void testRemoveFromTheOwningSide() {

        // verify initial
        {
            List<N> listN = em.createQuery("select t from N t", N.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(0, 1), listN, ReflectionComparatorMode.LENIENT_ORDER);
            List<M> listM = em.createQuery("select t from M t", M.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(1, 2), listM, ReflectionComparatorMode.LENIENT_ORDER);
            flushAndClear();
        }

        // remove
        {
            M m = em.find(M.class, 1);
            em.remove(m);
            flushAndClear();
        }

        // verify final
        {
            M m = em.find(M.class, 1);
            Assert.assertNull(m);
            N n = em.find(N.class, 1);
            Assert.assertNotNull(n);
            Assert.assertTrue(n.getListWithMs().isEmpty());
        }

    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testRemoveFromTheNonOwningSide() {

        // verify initial
        {
            List<N> listN = em.createQuery("select t from N t", N.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(0, 1), listN, ReflectionComparatorMode.LENIENT_ORDER);
            List<M> listM = em.createQuery("select t from M t", M.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(1, 2), listM, ReflectionComparatorMode.LENIENT_ORDER);
            flushAndClear();
        }

        // remove and verify expected exception is raised
        {
            N n = em.find(N.class, 1);
            em.remove(n);
            flushAndClear();
        }

    }

}
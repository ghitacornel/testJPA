package main.tests.relationships.many.to.many.list.cascade;

import entities.relationships.many.to.many.list.cascade.CascadeM;
import entities.relationships.many.to.many.list.cascade.CascadeN;
import main.tests.TransactionalSetup;
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

            CascadeN n1 = new CascadeN();
            n1.setId(1);
            n1.setName("n 1 name");
            objects.add(n1);

            CascadeM m1 = new CascadeM();
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
            List<CascadeN> listN = em.createQuery("select t from CascadeN t", CascadeN.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(0, 1), listN, ReflectionComparatorMode.LENIENT_ORDER);
            List<CascadeM> listM = em.createQuery("select t from CascadeM t", CascadeM.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(1, 2), listM, ReflectionComparatorMode.LENIENT_ORDER);
            flushAndClear();
        }

        // remove
        {
            CascadeM m = em.find(CascadeM.class, 1);
            em.remove(m);
            flushAndClear();
        }

        // verify final
        {
            verifyRemoveComplete();
        }

    }

    @Test
    public void testRemoveFromTheNonOwningSide() {

        // verify initial
        {
            List<CascadeN> listN = em.createQuery("select t from CascadeN t", CascadeN.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(0, 1), listN, ReflectionComparatorMode.LENIENT_ORDER);
            List<CascadeM> listM = em.createQuery("select t from CascadeM t", CascadeM.class).getResultList();
            ReflectionAssert.assertReflectionEquals(model.subList(1, 2), listM, ReflectionComparatorMode.LENIENT_ORDER);
            flushAndClear();
        }

        // remove
        {
            CascadeN n = em.find(CascadeN.class, 1);
            em.remove(n);
            flushAndClear();
        }

        // verify final
        {
            verifyRemoveComplete();
        }

        }

    private void verifyRemoveComplete() {
        CascadeM m = em.find(CascadeM.class, 1);
        Assert.assertNull(m);
        CascadeN n = em.find(CascadeN.class, 1);
        Assert.assertNull(n);
    }

}
package relationships.one.to.one.bidirectional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestInsertAWithCascadeToB extends TransactionalSetup {

    private A model = buildModel();

    private A buildModel() {

        A a = new A();
        a.setId(1);
        a.setName("this is a");

        B b = new B();
        b.setId(2);
        b.setName("this is b");
        b.setA(a);
        a.setB(b);

        return a;
    }

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery("select t from A t").getResultList().isEmpty());
        Assert.assertTrue(em.createQuery("select t from B t").getResultList().isEmpty());
    }

    @Test
    public void test() {

        em.persist(model);
        flushAndClear();

        A existing = em.find(A.class, model.getId());
        ReflectionAssert.assertReflectionEquals(model, existing, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(model.getB(), existing.getB());

    }
}

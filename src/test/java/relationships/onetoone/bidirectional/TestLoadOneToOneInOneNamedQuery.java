package relationships.onetoone.bidirectional;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestLoadOneToOneInOneNamedQuery extends TransactionalSetup {

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
        em.persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        A a = em.createNamedQuery("A.findWithB", A.class).setParameter(1, 1).getSingleResult();
        flushAndClear();

        ReflectionAssert.assertReflectionEquals(model, a);
        ReflectionAssert.assertReflectionEquals(model.getB(), a.getB());

    }
}

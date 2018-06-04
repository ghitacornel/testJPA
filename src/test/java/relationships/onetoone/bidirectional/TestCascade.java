package relationships.onetoone.bidirectional;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestCascade extends TransactionalSetup {

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
        verifyCorrespondingTableIsEmpty(A.class);
        verifyCorrespondingTableIsEmpty(B.class);
    }

    @Test
    public void testPersist() {

        // persist
        em.persist(model);
        flushAndClear();

        // verify cascade persist
        A existing = em.find(A.class, model.getId());
        ReflectionAssert.assertReflectionEquals(model, existing, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(model.getB(), existing.getB());

    }

    @Test
    public void testRemove() {

        em.persist(model);
        flushAndClear();

        // remove
        em.remove(em.find(A.class, model.getId()));
        flushAndClear();

        // verify cascade remove
        verifyCorrespondingTableIsEmpty(A.class);
        verifyCorrespondingTableIsEmpty(B.class);

    }
}

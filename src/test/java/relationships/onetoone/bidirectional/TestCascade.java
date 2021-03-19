package relationships.onetoone.bidirectional;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestCascade extends TransactionalSetup {

    private A parent = buildModel();

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
    public void testChildIsPersistedWhenParentIsPersisted() {

        // persist
        em.persist(parent);
        flushAndClear();

        // verify cascade persist
        A existing = em.find(A.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(parent, existing, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(parent.getB(), existing.getB());

    }

    @Test
    public void testChildIsRemovedWhenParentIsRemoved() {

        em.persist(parent);
        flushAndClear();

        // remove
        em.remove(em.find(A.class, parent.getId()));
        flushAndClear();

        // verify cascade remove
        verifyCorrespondingTableIsEmpty(A.class);
        verifyCorrespondingTableIsEmpty(B.class);

    }

    @Test
    public void testLoadParentEagerlyLoadsChildDueToNotowningTheForeignKey() {

        em.persist(parent);
        flushAndClear();

        // remove
        A a = em.find(A.class, parent.getId());
        System.out.println(a);
        System.out.println(a.getB());// verify with debug that child is already loaded
        flushAndClear();

    }
}

package relationships.onetoone.bidirectional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestCascade extends TransactionalSetup {

    private final A parent = buildModel();

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

    @BeforeEach
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
        org.assertj.core.api.Assertions.assertThat(parent).usingRecursiveComparison().isEqualTo(existing);
        org.assertj.core.api.Assertions.assertThat(parent.getB()).usingRecursiveComparison().isEqualTo(existing.getB());

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

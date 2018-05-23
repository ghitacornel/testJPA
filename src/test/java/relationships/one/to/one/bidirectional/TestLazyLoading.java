package relationships.one.to.one.bidirectional;

import relationships.one.to.one.bidirectional.A;
import relationships.one.to.one.bidirectional.B;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Persistence;

public class TestLazyLoading extends TransactionalSetup {

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
    public void testLazyDoesNotWork() {

        A a = em.find(A.class, model.getId());
        flushAndClear();

        // XXX a.b is marked as a LAZY association but due to the fact that A doesn't hold the foreign key
        // the LAZY marker is ignored hence the a.b is eagerly loaded
        Assert.assertTrue(Persistence.getPersistenceUtil().isLoaded(a.getB()));

    }

    @Test
    public void testLazyWorks() {

        B b = em.find(B.class, 2);
        flushAndClear();
        Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(b.getA()));

    }

}

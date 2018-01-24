package main.tests.relationships.one.to.one.bidirectional;

import entities.relationships.one.to.one.bidirectional.A;
import entities.relationships.one.to.one.bidirectional.B;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRemoveOneToOneWithCascade extends TransactionalSetup {

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

        Assert.assertFalse(em.createQuery("select t from A t").getResultList().isEmpty());
        Assert.assertFalse(em.createQuery("select t from B t").getResultList().isEmpty());

        em.remove(em.find(A.class, model.getId()));
        flushAndClear();

        Assert.assertTrue(em.createQuery("select t from A t").getResultList().isEmpty());
        Assert.assertTrue(em.createQuery("select t from B t").getResultList().isEmpty());

    }
}

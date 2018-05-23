package relationships.one.to.one.bidirectional;

import entities.relationships.one.to.one.bidirectional.A;
import entities.relationships.one.to.one.bidirectional.B;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class TestUpdateOneToOneWithCascade extends TransactionalSetup {

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

        A existingA1 = em.find(A.class, model.getId());
        {
            existingA1.setName("new name a");

            B b = new B();
            b.setId(4);
            b.setName("new name b");
            b.setA(existingA1);
            existingA1.setB(b);
        }

        em.merge(existingA1);
        flushAndClear();

        A existingA2 = em.find(A.class, model.getId());
        ReflectionAssert.assertReflectionEquals(existingA1, existingA2);
        ReflectionAssert.assertReflectionEquals(existingA1.getB(), existingA2.getB());

        // test old B is removed
        Assert.assertEquals(1, em.createQuery("select t from B t").getResultList().size());

    }
}

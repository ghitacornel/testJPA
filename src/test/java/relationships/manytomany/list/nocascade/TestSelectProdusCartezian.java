package relationships.manytomany.list.nocascade;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.List;

public class TestSelectProdusCartezian extends TransactionalSetup {

    private List<Object> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testProdusCartezian() {

        @SuppressWarnings("unchecked")
        List<Object[]> list = em.createQuery("select m , n from NoCascadeM m , NoCascadeN n ").getResultList();

        for (Object[] tuple : list) {
            Assert.assertEquals(2, tuple.length);
            System.out.println("tuple");
            for (Object o : tuple) {
                System.out.print(" = " + o);
            }
            System.out.println();
        }
        Assert.assertEquals(list.size(), em.createQuery("select m from NoCascadeM m").getResultList().size() * em.createQuery("select n from NoCascadeN n").getResultList().size());
    }

    // NOTE full inner join in terms of JPA doesn't return correct model values.
    // entities without a pair are not fetched hence incomplete model is loaded
}

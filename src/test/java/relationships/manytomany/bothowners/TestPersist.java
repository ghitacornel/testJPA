package relationships.manytomany.bothowners;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;

public class TestPersist extends TransactionalSetup {

    M m;
    N n1;
    N n2;

    @Before
    public void buildModel() {

        m = new M();
        m.setId(1);
        m.setName("m 1 name");

        n1 = new N();
        n1.setId(1);
        n1.setName("n 1 name");

        n2 = new N();
        n2.setId(2);
        n2.setName("n 2 name");

        n1.getListWithMs().add(m);
        n2.getListWithMs().add(m);
        m.getListWithNs().add(n1);
        m.getListWithNs().add(n2);

    }

    @Test(expected = PersistenceException.class)
    public void testPersistOnlyOneSide() {
        em.persist(m);
        flushAndClear();
    }

    @Test(expected = PersistenceException.class)
    public void testPersistPartially() {
        em.persist(m);
        em.persist(n1);
        flushAndClear();
    }

    @Test(expected = PersistenceException.class)
    public void testPersistAllAndFail() {

        // persist all
        em.persist(m);
        em.persist(n1);
        em.persist(n2);
        flushAndClear();

        // verify
//        {
//            List<Object[]> list = em.createNativeQuery("select * from MN_OK").getResultList();
//            for (Object[] objects : list) {
//                for (Object object : objects) {
//                    System.out.print(object + " ");
//                }
//                System.out.println("\n");
//            }
//
//        }
//
//        ReflectionAssert.assertReflectionEquals(m, em.find(M.class, m.getId()), ReflectionComparatorMode.LENIENT_ORDER);
//        ReflectionAssert.assertReflectionEquals(n1, em.find(N.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
//        ReflectionAssert.assertReflectionEquals(n2, em.find(N.class, n2.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
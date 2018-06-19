package relationships.manytomany.bothowners;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import javax.persistence.PersistenceException;

public class TestPersist extends TransactionalSetup {

    BothOwnerM m;
    BothOwnerN n1;
    BothOwnerN n2;

    @Before
    public void buildModel() {

        m = new BothOwnerM();
        m.setId(1);
        m.setName("m 1 name");

        n1 = new BothOwnerN();
        n1.setId(1);
        n1.setName("n 1 name");

        n2 = new BothOwnerN();
        n2.setId(2);
        n2.setName("n 2 name");

        n1.getListWithMs().add(m);
        n2.getListWithMs().add(m);
        m.getListWithNs().add(n1);
        m.getListWithNs().add(n2);

    }

    @Test(expected = PersistenceException.class)
    public void testPersistOnlyOneSideAndFail() {
        em.persist(m);
        flushAndClear();
    }

    @Test(expected = PersistenceException.class)
    public void testPersistPartiallyAndFail() {
        em.persist(m);
        em.persist(n1);
        flushAndClear();
    }

    @Test(expected = PersistenceException.class)
    public void testPersistAllAndFail() {

        em.persist(m);
        em.persist(n1);
        em.persist(n2);
        flushAndClear();

    }

    /**
     * This kind of mapping doesn't work as expected
     */
    @Test
    public void testPersistFirstEntitiesThenOnly1SideOfTheRelationshipAndOK() {

        m.getListWithNs().clear();
        n1.getListWithMs().clear();
        n2.getListWithMs().clear();

        // persist all
        em.persist(m);
        em.persist(n1);
        em.persist(n2);
        em.flush();

        m.getListWithNs().add(n1);
        m.getListWithNs().add(n2);
        // DO NOT SET counterpart references
        flushAndClear();

        // verify
        {// adjust the model to reflect expected changes
            n1.getListWithMs().add(m);
            n2.getListWithMs().add(m);
            em.flush();
        }
        ReflectionAssert.assertReflectionEquals(m, em.find(BothOwnerM.class, m.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n1, em.find(BothOwnerN.class, n1.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(n2, em.find(BothOwnerN.class, n2.getId()), ReflectionComparatorMode.LENIENT_ORDER);

    }

}
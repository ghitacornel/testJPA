package relationships.orphans.onetoone.bothways;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOOrphanBothWaysNotOwner a;
    OTOOrphanBothWaysOwner b;

    @Before
    public void setUp() {

        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysOwner.class);

        a = new OTOOrphanBothWaysNotOwner();
        a.setId(1);
        a.setName("a");

        b = new OTOOrphanBothWaysOwner();
        b.setId(1);
        b.setName("b");

        a.setB(b);
        b.setA(a);

        persist(a, b);
        flushAndClear();

    }

    @Test
    public void testRemoveOrphanB() {

        // mark B as orphan
        em.find(OTOOrphanBothWaysOwner.class, b.getId()).setA(null);
        flushAndClear();

        // regardless which is removed since orphanRemoval flag is used on both side both entities are removed
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysOwner.class);

    }

    @Test
    public void testRemoveOrphanA() {

        // mark A as orphan
        em.find(OTOOrphanBothWaysNotOwner.class, a.getId()).setB(null);
        flushAndClear();

        // regardless which is removed since orphanRemoval flag is used on both side both entities are removed
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysOwner.class);

    }
}

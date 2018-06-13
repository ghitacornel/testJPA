package relationships.orphans.onetoone.bothways;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOOrphanBothWaysNotOwner notOwner;
    OTOOrphanBothWaysOwner owner;

    @Before
    public void setUp() {

        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysOwner.class);

        notOwner = new OTOOrphanBothWaysNotOwner();
        notOwner.setId(1);
        notOwner.setName("notOwner");

        owner = new OTOOrphanBothWaysOwner();
        owner.setId(1);
        owner.setName("owner");

        notOwner.setB(owner);
        owner.setA(notOwner);

        persist(notOwner, owner);
        flushAndClear();

    }

    @Test
    public void testRemoveOrphanOwner() {

        // mark owner as orphan
        em.find(OTOOrphanBothWaysOwner.class, owner.getId()).setA(null);
        flushAndClear();

        // regardless which is removed since orphanRemoval flag is used on both side both entities are removed
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysOwner.class);

    }

    @Test
    public void testRemoveOrphanNotOwner() {

        // mark not owner as orphan
        em.find(OTOOrphanBothWaysNotOwner.class, notOwner.getId()).setB(null);
        flushAndClear();

        // regardless which is removed since orphanRemoval flag is used on both side both entities are removed
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanBothWaysOwner.class);

    }
}

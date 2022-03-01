package relationships.orphans.onetoone.notowningside;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOOrphanNotOwningSideNotOwner notOwner;
    OTOOrphanNotOwningSideOwner owner;

    @BeforeEach
    public void setUp() {

        verifyCorrespondingTableIsEmpty(OTOOrphanNotOwningSideNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanNotOwningSideOwner.class);

        notOwner = new OTOOrphanNotOwningSideNotOwner();
        notOwner.setId(1);
        notOwner.setName("notOwner");

        owner = new OTOOrphanNotOwningSideOwner();
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
        em.find(OTOOrphanNotOwningSideOwner.class, owner.getId()).setA(null);
        flushAndClear();

        // since we remove the part not flagged as orphanRemoval only the link is removed

        {// adjust model to match expectations
            owner.setA(null);
        }
        ReflectionAssert.assertReflectionEquals(owner, em.find(OTOOrphanNotOwningSideOwner.class, owner.getId()));

        {// adjust model to match expectations
            notOwner.setB(null);
        }
        ReflectionAssert.assertReflectionEquals(notOwner, em.find(OTOOrphanNotOwningSideNotOwner.class, notOwner.getId()));

    }

    @Test
    public void testRemoveOrphanNotOwner() {

        // mark not owner as orphan
        em.find(OTOOrphanNotOwningSideNotOwner.class, notOwner.getId()).setB(null);
        flushAndClear();

        // since we remove the part flagged as orphanRemoval the other side is removed regardless that it is the owning side

        Assertions.assertNull(em.find(OTOOrphanNotOwningSideOwner.class, owner.getId()));
        {// adjust model to match expectations
            notOwner.setB(null);
        }
        ReflectionAssert.assertReflectionEquals(notOwner, em.find(OTOOrphanNotOwningSideNotOwner.class, notOwner.getId()));

    }
}

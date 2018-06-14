package relationships.orphans.onetoone.owningside;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestRemoveOrphans extends TransactionalSetup {

    OTOOrphanOwningSideNotOwner notOwner;
    OTOOrphanOwningSideOwner owner;

    @Before
    public void setUp() {

        verifyCorrespondingTableIsEmpty(OTOOrphanOwningSideNotOwner.class);
        verifyCorrespondingTableIsEmpty(OTOOrphanOwningSideOwner.class);

        notOwner = new OTOOrphanOwningSideNotOwner();
        notOwner.setId(1);
        notOwner.setName("notOwner");

        owner = new OTOOrphanOwningSideOwner();
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
        em.find(OTOOrphanOwningSideOwner.class, owner.getId()).setA(null);
        flushAndClear();

        Assert.assertNull(em.find(OTOOrphanOwningSideNotOwner.class, notOwner.getId()));
        {// adjust model to match expectations
            owner.setA(null);
        }
        ReflectionAssert.assertReflectionEquals(owner, em.find(OTOOrphanOwningSideOwner.class, owner.getId()));

    }

    @Test
    public void testRemoveOrphanNotOwner() {

        // mark not owner as orphan
        em.find(OTOOrphanOwningSideNotOwner.class, notOwner.getId()).setB(null);
        flushAndClear();

        // observe nothing happens since A is not the owning side
        ReflectionAssert.assertReflectionEquals(notOwner, em.find(OTOOrphanOwningSideNotOwner.class, notOwner.getId()));
        ReflectionAssert.assertReflectionEquals(owner, em.find(OTOOrphanOwningSideOwner.class, owner.getId()));

    }
}

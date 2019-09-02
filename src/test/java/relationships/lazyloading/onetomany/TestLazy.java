package relationships.lazyloading.onetomany;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import javax.persistence.Persistence;

public class TestLazy extends TransactionalSetup {

    OTOMLazyParent parent;
    OTOMLazyChild child;

    @Before
    public void setUp() {

        parent = new OTOMLazyParent();
        parent.setId(1);
        parent.setName("m");

        child = new OTOMLazyChild();
        child.setId(1);
        child.setName("n");

        child.setParent(parent);
        parent.getChildren().add(child);

        persist(parent, child);
        flushAndClear();

    }

    @Test
    public void testLazyLoaded() {

        OTOMLazyParent existingParent = em.find(OTOMLazyParent.class, parent.getId());
        flushAndClear();
        Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingParent.getChildren()));

        OTOMLazyChild existingChild = em.find(OTOMLazyChild.class, child.getId());
        flushAndClear();
        Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingChild.getParent()));

    }

    @Test
    public void testForceEager() {

        OTOMLazyParent existingParent = em.find(OTOMLazyParent.class, parent.getId());
        existingParent.getChildren().size();// force proxy
        flushAndClear();
        Assert.assertTrue(Persistence.getPersistenceUtil().isLoaded(existingParent.getChildren()));

        OTOMLazyChild existingChild = em.find(OTOMLazyChild.class, child.getId());
        existingChild.getParent().getName();// force proxy
        flushAndClear();
        Assert.assertTrue(Persistence.getPersistenceUtil().isLoaded(existingChild.getParent()));

    }

    @Test
    public void testForceEagerOnAlreadyLoadedFieldDoesNotTriggerTheForcedLoadingOfTheWholeEntity() {

        OTOMLazyChild existingChild = em.find(OTOMLazyChild.class, child.getId());
        existingChild.getParent().getId();// try to force proxy on already loaded field
        flushAndClear();

        Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existingChild.getParent()));

    }

}

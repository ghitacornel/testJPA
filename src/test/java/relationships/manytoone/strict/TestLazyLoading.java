package relationships.manytoone.strict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import javax.persistence.Persistence;

public class TestLazyLoading extends TransactionalSetup {

    private MTOOStrictChild child;

    @Before
    public void before() {

        MTOOStrictParent parent = new MTOOStrictParent();
        parent.setId(1);
        parent.setName("parent strict 1");
        persist(parent);

        child = new MTOOStrictChild();
        child.setId(1);
        child.setName("child name");
        child.setParent(parent);
        persist(child);

        flushAndClear();
    }

    @Test
    public void testLazyLoadingWorks() {

        MTOOStrictChild existing1 = em.find(MTOOStrictChild.class, child.getId());
        flushAndClear();
        Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existing1.getParent()));

        MTOOStrictChild existing2 = em.find(MTOOStrictChild.class, child.getId());
        existing2.getParent().getId();// access id of parent and see still the parent is not loaded
        flushAndClear();
        Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existing2.getParent()));

        MTOOStrictChild existing3 = em.find(MTOOStrictChild.class, child.getId());
        existing3.getParent().getName();// just access a not loaded field
        flushAndClear();
        Assert.assertTrue(Persistence.getPersistenceUtil().isLoaded(existing3.getParent()));

    }

}

package relationships.one.to.one.unidirectional.child.parent.nocascade;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import setup.TransactionalSetup;

public class TestOTOUCPNoCascade extends TransactionalSetup {

    @Test
    public void testInsertChildAndNoCascadeToParent() {

        OTOUCPNoCascadeParent parent = new OTOUCPNoCascadeParent();
        parent.setId(1);
        parent.setName("parent");

        OTOUCPNoCascadeChild child = new OTOUCPNoCascadeChild();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(parent);// TODO remove this insert and see insert of child fail
        em.persist(child);
        flushAndClear();

        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOUCPNoCascadeParent.class, 1));
        ReflectionAssert.assertReflectionEquals(child, em.find(OTOUCPNoCascadeChild.class, 1));

    }

    @Test
    public void testRemoveChildAndNoCascadeToParentStillRemovesTheParent() {

        OTOUCPNoCascadeParent parent = new OTOUCPNoCascadeParent();
        parent.setId(1);
        parent.setName("parent");

        OTOUCPNoCascadeChild child = new OTOUCPNoCascadeChild();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(parent);
        em.persist(child);
        flushAndClear();

        em.remove(em.find(OTOUCPNoCascadeChild.class, 1));// remove only the child
        flushAndClear();

        Assert.assertNull(em.find(OTOUCPNoCascadeChild.class, 1));
        Assert.assertNull(em.find(OTOUCPNoCascadeParent.class, 1));

    }
}

package relationships.one.to.one.unidirectional.child.parent.cascade;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

public class TestOTOUCPCascade extends TransactionalSetup {

    @Test
    public void testInsertChildAndCascadeToParent() {

        OTOUCPCascadeParent parent = new OTOUCPCascadeParent();
        parent.setId(1);
        parent.setName("parent");

        OTOUCPCascadeChild child = new OTOUCPCascadeChild();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);// TODO insert only the child and see the parent is inserted also
        flushAndClear();

        ReflectionAssert.assertReflectionEquals(parent, em.find(OTOUCPCascadeParent.class, 1));
        ReflectionAssert.assertReflectionEquals(child, em.find(OTOUCPCascadeChild.class, 1));

    }

    @Test
    public void testRemoveChildAndCascadeToParent() {

        OTOUCPCascadeParent parent = new OTOUCPCascadeParent();
        parent.setId(1);
        parent.setName("parent");

        OTOUCPCascadeChild child = new OTOUCPCascadeChild();
        child.setId(1);
        child.setName("child");
        child.setParent(parent);

        em.persist(child);
        flushAndClear();

        em.remove(em.find(OTOUCPCascadeChild.class, 1));// TODO remove only the child and see the parent is removed also
        flushAndClear();

        Assert.assertNull(em.find(OTOUCPCascadeParent.class, 1));
        Assert.assertNull(em.find(OTOUCPCascadeChild.class, 1));

    }
}

package entities.listener.circular;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.Comparator;

public class TestCircularEntityListenerPropagation extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(ParentCircularWithListener.class);
        verifyCorrespondingTableIsEmpty(ChildCircularWithListener.class);
    }

    @Test
    public void testPersistListenerIsInvoked() {

        ParentCircularWithListener parent = new ParentCircularWithListener();
        parent.setId(1);
        parent.setName("parent 1");

        ChildCircularWithListener child1 = new ChildCircularWithListener();
        child1.setId(1);
        child1.setName("child 1");

        ChildCircularWithListener child2 = new ChildCircularWithListener();
        child2.setId(2);
        child2.setName("child 2");

        child1.setParent(parent);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        em.persist(parent);
        flushAndClear();

        ParentCircularWithListener parentPersisted = em.find(ParentCircularWithListener.class, parent.getId());
        Assert.assertEquals(parent.getId(), parentPersisted.getId());
        Assert.assertEquals(parent.getName(), parentPersisted.getName());
        Assert.assertEquals("parent own set update", parentPersisted.getTestColumnOwn());
        Assert.assertEquals("set by child 1 persist with name child 1", parentPersisted.getTestColumnChild1());
        Assert.assertEquals("set by child 2 persist with name child 2", parentPersisted.getTestColumnChild2());
        Assert.assertEquals(2, parentPersisted.getChildren().size());

        parent.getChildren().sort(Comparator.comparing(ChildCircularWithListener::getId));

        Assert.assertEquals(parent.getChildren().get(0).getId(), child1.getId());
        Assert.assertEquals(parent.getChildren().get(0).getName(), child1.getName());
        Assert.assertEquals("child own set update", parent.getChildren().get(0).getTestColumnOwn());
        Assert.assertEquals("parent update", parent.getChildren().get(0).getTestColumnOther());

        Assert.assertEquals(parent.getChildren().get(1).getId(), child2.getId());
        Assert.assertEquals(parent.getChildren().get(1).getName(), child2.getName());
        Assert.assertEquals("child own set update", parent.getChildren().get(1).getTestColumnOwn());
        Assert.assertEquals("parent update", parent.getChildren().get(1).getTestColumnOther());

    }

    @Test
    public void testUpdateListenerIsInvoked() {

        // PERSIST
        ParentCircularWithListener parent = new ParentCircularWithListener();
        parent.setId(1);
        parent.setName("parent 1");

        ChildCircularWithListener child1 = new ChildCircularWithListener();
        child1.setId(1);
        child1.setName("child 1");

        ChildCircularWithListener child2 = new ChildCircularWithListener();
        child2.setId(2);
        child2.setName("child 2");

        child1.setParent(parent);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        em.persist(parent);
        flushAndClear();

        // UPDATE
        parent = em.find(ParentCircularWithListener.class, parent.getId());
        parent.setName("parent 1 name");
        parent.getChildren().sort(Comparator.comparing(ChildCircularWithListener::getId));
        parent.getChildren().get(0).setName("child 1 name");
        parent.getChildren().get(1).setName("child 2 name");
        flushAndClear();

        // verify
        ParentCircularWithListener parentPersisted = em.find(ParentCircularWithListener.class, parent.getId());
        Assert.assertEquals("parent own set update", parentPersisted.getTestColumnOwn());
        Assert.assertEquals(2, parentPersisted.getChildren().size());
        Assert.assertEquals("set by child 1 persist with name child 1", parentPersisted.getTestColumnChild1());// TODO check this odd behavior
        Assert.assertEquals("set by child 2 persist with name child 2", parentPersisted.getTestColumnChild2());// TODO check this odd behavior
        // TODO check this odd behavior
        // TODO should be set to "update" but due to the fact that parent was updated first these changes won't be propagated

        parent.getChildren().sort(Comparator.comparing(ChildCircularWithListener::getId));

        Assert.assertEquals("child 1 name", parent.getChildren().get(0).getName());
        Assert.assertEquals("child own set update", parent.getChildren().get(0).getTestColumnOwn());
        Assert.assertEquals("parent update", parent.getChildren().get(0).getTestColumnOther());
        Assert.assertEquals("child 2 name", parent.getChildren().get(1).getName());
        Assert.assertEquals("child own set update", parent.getChildren().get(1).getTestColumnOwn());
        Assert.assertEquals("parent update", parent.getChildren().get(1).getTestColumnOther());

    }

}

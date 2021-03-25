package entities.listener.parentchildren;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.Comparator;

public class TestEntityListenerPropagation extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(ParentWithListener.class);
        verifyCorrespondingTableIsEmpty(ChildWithListener.class);
    }

    @Test
    public void testPersistListenerIsInvoked() {

        ParentWithListener parent = new ParentWithListener();
        parent.setId(1);
        parent.setName("parent 1");

        ChildWithListener child1 = new ChildWithListener();
        child1.setId(1);
        child1.setName("child 1");

        ChildWithListener child2 = new ChildWithListener();
        child2.setId(2);
        child2.setName("child 2");

        child1.setParent(parent);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        em.persist(parent);
        flushAndClear();

        ParentWithListener parentPersisted = em.find(ParentWithListener.class, parent.getId());
        Assert.assertEquals(parent.getId(), parentPersisted.getId());
        Assert.assertEquals(parent.getName(), parentPersisted.getName());
        Assert.assertEquals("parent own set persist", parentPersisted.getTestColumnOwn());
        Assert.assertEquals(2, parentPersisted.getChildren().size());

        parent.getChildren().sort(Comparator.comparing(ChildWithListener::getId));

        Assert.assertEquals(parent.getChildren().get(0).getId(), child1.getId());
        Assert.assertEquals(parent.getChildren().get(0).getName(), child1.getName());
        Assert.assertEquals("child own set persist", parent.getChildren().get(0).getTestColumnOwn());
        Assert.assertEquals("parent persist", parent.getChildren().get(0).getTestColumnOther());

        Assert.assertEquals(parent.getChildren().get(1).getId(), child2.getId());
        Assert.assertEquals(parent.getChildren().get(1).getName(), child2.getName());
        Assert.assertEquals("child own set persist", parent.getChildren().get(1).getTestColumnOwn());
        Assert.assertEquals("parent persist", parent.getChildren().get(1).getTestColumnOther());

    }

    @Test
    public void testUpdateListenerIsInvoked() {

        // PERSIST
        ParentWithListener parent = new ParentWithListener();
        parent.setId(1);
        parent.setName("parent 1");

        ChildWithListener child1 = new ChildWithListener();
        child1.setId(1);
        child1.setName("child 1");

        ChildWithListener child2 = new ChildWithListener();
        child2.setId(2);
        child2.setName("child 2");

        child1.setParent(parent);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        em.persist(parent);
        flushAndClear();

        // UPDATE
        parent = em.find(ParentWithListener.class, parent.getId());
        parent.setName("parent 1 name");
        parent.getChildren().sort(Comparator.comparing(ChildWithListener::getId));
        parent.getChildren().get(0).setName("child 1 name");
        parent.getChildren().get(1).setName("child 2 name");
        flushAndClear();

        // verify
        ParentWithListener parentPersisted = em.find(ParentWithListener.class, parent.getId());
        Assert.assertEquals("parent own set update", parentPersisted.getTestColumnOwn());
        Assert.assertEquals(2, parentPersisted.getChildren().size());

        parent.getChildren().sort(Comparator.comparing(ChildWithListener::getId));

        Assert.assertEquals("child own set update", parent.getChildren().get(0).getTestColumnOwn());
        Assert.assertEquals("parent update", parent.getChildren().get(0).getTestColumnOther());
        Assert.assertEquals("child own set update", parent.getChildren().get(1).getTestColumnOwn());
        Assert.assertEquals("parent update", parent.getChildren().get(1).getTestColumnOther());

    }

}

package relationships.one.to.many.bidirectional.list;

import entities.relationships.one.to.many.bidirectional.list.Child;
import entities.relationships.one.to.many.bidirectional.list.Parent;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestRemoveParentWithCascadeToChildren extends TransactionalSetup {

    private Parent model = buildModel();

    private Parent buildModel() {

        Parent parent = new Parent();
        parent.setId(1);
        parent.setName("parinte 1");
        parent.setChildren(new ArrayList<>());

        for (int i = 1; i <= 3; i++) {
            Child child = new Child();
            child.setId(i);
            child.setName("copil " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        em.persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        em.remove(em.find(Parent.class, model.getId()));
        flushAndClear();

        Assert.assertNull(em.find(Parent.class, model.getId()));
        for (Child child : model.getChildren()) {
            Assert.assertNull(em.find(Child.class, child.getId()));
        }

    }
}

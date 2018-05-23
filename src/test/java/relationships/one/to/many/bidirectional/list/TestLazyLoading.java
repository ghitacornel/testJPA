package relationships.one.to.many.bidirectional.list;

import entities.relationships.one.to.many.bidirectional.list.Child;
import entities.relationships.one.to.many.bidirectional.list.Parent;
import setup.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestLazyLoading extends TransactionalSetup {

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

    @Test(expected = Exception.class)
    public void testLazy() {

        Parent parent = em.find(Parent.class, model.getId());

        // specified explicitly here (optional if flush and clear is used)

        em.detach(parent);
        flushAndClear();

        // detached entity => proxy problem => lazy fails
        parent.getChildren().size();

        // no problem will occur with eager loading
        // XXX note that defaults for eager/lazy are not always those you expect

    }

}

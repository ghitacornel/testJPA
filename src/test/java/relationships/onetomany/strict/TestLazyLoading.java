package relationships.onetomany.strict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestLazyLoading extends TransactionalSetup {

    private Parent parent = buildModel();

    private Parent buildModel() {

        Parent parent = new Parent();
        parent.setId(1);
        parent.setName("parent 1");
        parent.setChildren(new ArrayList<>());

        for (int i = 1; i <= 3; i++) {
            Child child = new Child();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        em.persist(parent);
        flushAndClear();
    }

    @Test(expected = Exception.class)
    public void testLazyLoadingFailOnNotLoadedRelationshipsOfADetachedEntity() {

        Parent existing = em.find(Parent.class, parent.getId());
        flushAndClear();

        // proxy activated over a detached entity => proxy problem => lazy fails
        existing.getChildren().size();

        // no problem will occur with eager loading
        // XXX note that defaults for eager/lazy are not always those you expect

    }

    @Test
    public void testLazyLoading() {

        Parent existing = em.find(Parent.class, parent.getId());
        existing.getChildren().size();// force the proxy to fetch the data from the database
        flushAndClear();

        Assert.assertEquals(3, existing.getChildren().size());

    }
}

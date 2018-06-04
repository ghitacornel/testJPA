package queries.named.nativ;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import relationships.onetomany.list.Child;
import relationships.onetomany.list.Parent;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestNamedNativeQueryWithSqlResultSetMapping extends TransactionalSetup {

    private Parent model = buildModel();

    private Parent buildModel() {

        Parent parent = new Parent();
        parent.setId(1);
        parent.setName("parent " + parent.getId());
        parent.setChildren(new ArrayList<>());

        for (int i = 1; i <= 3; i++) {
            Child child = new Child();
            child.setId(i);
            child.setName("child " + +child.getId());
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test() {

        // fetch
        List<Object[]> list = em.createNamedQuery("Parent.findWithChildrenNative").getResultList();

        // verify, order is ignored
        List<Child> fetchedChildren = new ArrayList<>();
        for (Object[] objects : list) {

            // parent is 'retrieved' many times
            Parent parent = (Parent) objects[0];
            Child child = (Child) objects[1];
            fetchedChildren.add(child);

            Assert.assertEquals(model.getId(), parent.getId());
            Assert.assertEquals(model.getName(), parent.getName());

        }

        // verify fetched children
        ReflectionAssert.assertReflectionEquals(model.getChildren(), fetchedChildren, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFullyFetchedEntitiesAreManaged() {

        // fetch
        List<Object[]> existing = em.createNamedQuery("Parent.findWithChildrenNative").getResultList();

        // alter
        for (Object[] objects : existing) {
            Parent parent = (Parent) objects[0];
            parent.setName("new parent name " + parent.getId());
            Child child = (Child) objects[1];
            child.setName("new child name " + child.getId());
        }

        flushAndClear();

        // fetch again
        List<Object[]> updated = em.createNamedQuery("Parent.findWithChildrenNative").getResultList();

        // adjust model for verification
        {
            model.setName("new parent name " + model.getId());
            for (Child child : model.getChildren()) {
                child.setName("new child name " + child.getId());
            }

        }
        // verify, order is ignored
        List<Child> fetchedChildren = new ArrayList<>();
        for (Object[] objects : updated) {

            // parent is 'retrieved' many times
            Parent parent = (Parent) objects[0];
            Child child = (Child) objects[1];
            fetchedChildren.add(child);

            Assert.assertEquals(model.getId(), parent.getId());
            Assert.assertEquals(model.getName(), parent.getName());

        }

        // verify fetched children
        ReflectionAssert.assertReflectionEquals(model.getChildren(), fetchedChildren, ReflectionComparatorMode.LENIENT_ORDER);


    }

}

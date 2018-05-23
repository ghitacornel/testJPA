package xxx.queries.named.nativ;

import entities.relationships.one.to.many.bidirectional.list.Child;
import entities.relationships.one.to.many.bidirectional.list.Parent;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestNamedNativeQueryWithSqlResultSetMapping extends TransactionalSetup {

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

}

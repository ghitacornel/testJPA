package relationships.one.to.many.bidirectional.list;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestLoadParentAndChildrenInOneNamedQuery extends
        TransactionalSetup {

    private Parent model = buildModel();

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
        em.persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        Parent parent = em.createNamedQuery("Parent.findWithChildren", Parent.class).setParameter(1, 1).getSingleResult();

        ReflectionAssert.assertReflectionEquals(model, parent, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(model.getChildren(), parent.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

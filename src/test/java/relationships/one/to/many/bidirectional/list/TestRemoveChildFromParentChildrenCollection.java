package relationships.one.to.many.bidirectional.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestRemoveChildFromParentChildrenCollection extends TransactionalSetup {

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
    public void testRemoveDirectlyFromCollection() {

        Parent existing1 = em.find(Parent.class, model.getId());
        existing1.getChildren().remove(1);
        flushAndClear();

        Parent existing2 = em.find(Parent.class, model.getId());
        Parent expectedParent = buildModel();
        expectedParent.getChildren().remove(1);
        ReflectionAssert.assertReflectionEquals(expectedParent.getChildren(), existing2.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

        // child is removed since it is now an orphan
        Child child = em.find(Child.class, buildModel().getChildren().get(1).getId());
        Assert.assertNull(child);

    }

    @Test
    public void testRemoveFromEMAndThenFromCollection() {

        Parent existing1 = em.find(Parent.class, model.getId());
        em.remove(existing1.getChildren().get(1));
        existing1.getChildren().remove(1);//without this it won't work
        flushAndClear();

        Parent existing2 = em.find(Parent.class, model.getId());
        Parent expectedParent = buildModel();
        expectedParent.getChildren().remove(1);
        ReflectionAssert.assertReflectionEquals(expectedParent.getChildren(), existing2.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

package relationships.one.to.many.bidirectional.list;

import entities.relationships.one.to.many.bidirectional.list.Child;
import entities.relationships.one.to.many.bidirectional.list.Parent;
import setup.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;

public class TestRemoveChildFromParentChildrenCollection extends TransactionalSetup {

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
    public void testRemoveDirectlyFromCollection() {

        Parent existing1 = em.find(Parent.class, model.getId());
        existing1.getChildren().remove(1);
        flushAndClear();

        Parent existing2 = em.find(Parent.class, model.getId());
        Parent expectedParent = buildModel();
        expectedParent.getChildren().remove(1);
        ReflectionAssert.assertReflectionEquals(expectedParent.getChildren(), existing2.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);
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

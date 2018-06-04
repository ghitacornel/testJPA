package relationships.onetomany.strict;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestUpdateParentAndChildren extends TransactionalSetup {

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

        Parent existing1 = em.find(Parent.class, model.getId());

        {
            existing1.setName("new name");

            Child child = new Child();
            child.setId(4);
            child.setName("child 4");
            child.setParent(existing1);
            existing1.getChildren().add(child);

            existing1.getChildren().get(0).setName("new name 1");
        }

        em.merge(existing1);
        flushAndClear();

        Parent existing2 = em.find(Parent.class, model.getId());
        ReflectionAssert.assertReflectionEquals(existing1, existing2, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(existing1.getChildren(), existing2.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

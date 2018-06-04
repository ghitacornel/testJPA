package relationships.onetomany.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestInsertParentWithCascadeToChildren extends TransactionalSetup {

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
        Assert.assertNull(em.find(Parent.class, model.getId()));
        for (Child child : model.getChildren()) {
            Assert.assertNull(em.find(Child.class, child.getId()));
        }
    }

    @Test
    public void test() {

        em.persist(model);
        flushAndClear();

        Parent existing = em.find(Parent.class, model.getId());
        ReflectionAssert.assertReflectionEquals(model, existing, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(model.getChildren(), existing.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

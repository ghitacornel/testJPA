package relationships.onetomany.list;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestInsertParentWithCascadeToChildren extends TransactionalSetup {

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
        verifyCorrespondingTableIsEmpty(Parent.class);
        verifyCorrespondingTableIsEmpty(Child.class);
    }

    @Test
    public void test() {

        em.persist(parent);
        flushAndClear();

        Parent existing = em.find(Parent.class, parent.getId());
        ReflectionAssert.assertReflectionEquals(parent, existing, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(parent.getChildren(), existing.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

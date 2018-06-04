package relationships.onetomany.joinonnopk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;

public class TestJONPK extends TransactionalSetup {

    private JONPKParent model = buildModel();

    private JONPKParent buildModel() {

        JONPKParent parent = new JONPKParent();
        parent.setId(1);
        parent.setName("parent 1");
        parent.setChildren(new ArrayList<>());

        for (int i = 1; i <= 3; i++) {
            JONPKChild child = new JONPKChild();
            child.setId(i);
            child.setName("child " + i);
            child.setParent(parent);
            parent.getChildren().add(child);
        }

        return parent;
    }

    @Before
    public void before() {
        Assert.assertNull(em.find(JONPKParent.class, model.getId()));
        for (JONPKChild child : model.getChildren()) {
            Assert.assertNull(em.find(JONPKChild.class, child.getId()));
        }
    }

    @Test
    public void test() {

        em.persist(model);
        flushAndClear();

        JONPKParent existing = em.find(JONPKParent.class, model.getId());
        ReflectionAssert.assertReflectionEquals(model, existing, ReflectionComparatorMode.LENIENT_ORDER);
        ReflectionAssert.assertReflectionEquals(model.getChildren(), existing.getChildren(), ReflectionComparatorMode.LENIENT_ORDER);

    }
}

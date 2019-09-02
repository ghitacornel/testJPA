package relationships.onetoone.childPKequalsparentPK;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestPersist extends TransactionalSetup {

    private PKParent model = buildModel();

    private PKParent buildModel() {

        PKParent parent = new PKParent();
        parent.setId(1);
        parent.setName("parent 1");

        PKChild child = new PKChild();
        child.setId(1);
        child.setName("child " + 1);
        child.setParent(parent);

        return parent;
    }

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(PKParent.class);
        verifyCorrespondingTableIsEmpty(PKChild.class);
    }

    @Test
    public void testSelectAll() {

        em.persist(model);
        flushAndClear();

        PKParent existing = em.find(PKParent.class, model.getId());
        ReflectionAssert.assertReflectionEquals(model, existing, ReflectionComparatorMode.LENIENT_ORDER);

    }
}

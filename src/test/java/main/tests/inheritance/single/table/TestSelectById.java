package main.tests.inheritance.single.table;

import entities.inheritance.single.InheritanceSingleTableSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.List;

/**
 * observe that NO additional SQL joins are performed
 *
 * @author Cornel
 */
public class TestSelectById extends TransactionalSetup {

    private List<InheritanceSingleTableSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testSubclassA() {

        InheritanceSingleTableSuperClass result = em.find(InheritanceSingleTableSuperClass.class, model.get(0).getId());

        ReflectionAssert.assertReflectionEquals(model.get(0), result);

    }

    @Test
    public void testSubclassB() {

        InheritanceSingleTableSuperClass result = em.find(InheritanceSingleTableSuperClass.class, model.get(3).getId());

        ReflectionAssert.assertReflectionEquals(model.get(3), result);

    }

}

package main.tests.inheritance.single.table;

import entities.inheritance.single.table.InheritanceSingleTableConcreteClassB;
import entities.inheritance.single.table.InheritanceSingleTableSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.List;

public class TestSelectAllB extends TransactionalSetup {

    private List<InheritanceSingleTableSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        List<InheritanceSingleTableConcreteClassB> list = em.createQuery(
                "select t from InheritanceSingleTableConcreteClassB t", InheritanceSingleTableConcreteClassB.class)
                .getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(3, model.size()), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

}

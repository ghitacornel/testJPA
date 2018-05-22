package xxx.inheritance.table;

import entities.inheritance.single.InheritanceSingleTableConcreteClassA;
import entities.inheritance.single.InheritanceSingleTableSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.List;

public class TestSelectAllA extends TransactionalSetup {

    private List<InheritanceSingleTableSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        List<InheritanceSingleTableConcreteClassA> list = em.createQuery("select t from InheritanceSingleTableConcreteClassA t", InheritanceSingleTableConcreteClassA.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(0, 3), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

}

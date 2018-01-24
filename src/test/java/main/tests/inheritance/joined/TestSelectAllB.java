package main.tests.inheritance.joined;

import entities.inheritance.joined.InheritanceJoinedTablesConcreteClassB;
import entities.inheritance.joined.InheritanceJoinedTablesSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.List;

public class TestSelectAllB extends TransactionalSetup {

    private List<InheritanceJoinedTablesSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        List<InheritanceJoinedTablesConcreteClassB> list = em.createQuery(
                "select t from InheritanceJoinedTablesConcreteClassB t",
                InheritanceJoinedTablesConcreteClassB.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(3, 6), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

}

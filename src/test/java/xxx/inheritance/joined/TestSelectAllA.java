package xxx.inheritance.joined;

import entities.inheritance.joined.InheritanceJoinedTablesConcreteClassA;
import entities.inheritance.joined.InheritanceJoinedTablesSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.List;

public class TestSelectAllA extends TransactionalSetup {

    private List<InheritanceJoinedTablesSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        List<InheritanceJoinedTablesConcreteClassA> list = em.createQuery(
                "select t from InheritanceJoinedTablesConcreteClassA t",
                InheritanceJoinedTablesConcreteClassA.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(0, 3), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

}

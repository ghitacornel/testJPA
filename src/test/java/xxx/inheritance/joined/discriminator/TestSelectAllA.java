package xxx.inheritance.joined.discriminator;

import entities.inheritance.joined.discriminator.InheritanceJoinedTablesDiscriminatorConcreteClassA;
import entities.inheritance.joined.discriminator.InheritanceJoinedTablesDiscriminatorSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.List;

public class TestSelectAllA extends TransactionalSetup {

    private List<InheritanceJoinedTablesDiscriminatorSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        List<InheritanceJoinedTablesDiscriminatorConcreteClassA> list = em.createQuery(
                "select t from InheritanceJoinedTablesDiscriminatorConcreteClassA t",
                InheritanceJoinedTablesDiscriminatorConcreteClassA.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(0, 3), list, ReflectionComparatorMode.LENIENT_ORDER);
    }

}

package xxx.inheritance.joined.discriminator;

import entities.inheritance.joined.discriminator.InheritanceJoinedTablesDiscriminatorConcreteClassB;
import entities.inheritance.joined.discriminator.InheritanceJoinedTablesDiscriminatorSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.List;

public class TestSelectAllB extends TransactionalSetup {

    private List<InheritanceJoinedTablesDiscriminatorSuperClass> model = TestSelectAll.buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        List<InheritanceJoinedTablesDiscriminatorConcreteClassB> list = em.createQuery(
                "select t from InheritanceJoinedTablesDiscriminatorConcreteClassB t",
                InheritanceJoinedTablesDiscriminatorConcreteClassB.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model.subList(3, 6), list, ReflectionComparatorMode.LENIENT_ORDER);
    }

}

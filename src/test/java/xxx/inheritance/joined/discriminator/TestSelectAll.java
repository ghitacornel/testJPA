package xxx.inheritance.joined.discriminator;

import entities.inheritance.joined.discriminator.InheritanceJoinedTablesDiscriminatorConcreteClassA;
import entities.inheritance.joined.discriminator.InheritanceJoinedTablesDiscriminatorConcreteClassB;
import entities.inheritance.joined.discriminator.InheritanceJoinedTablesDiscriminatorSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private List<InheritanceJoinedTablesDiscriminatorSuperClass> model = buildModel();

    static List<InheritanceJoinedTablesDiscriminatorSuperClass> buildModel() {
        List<InheritanceJoinedTablesDiscriminatorSuperClass> list = new ArrayList<>();
        long i;
        for (i = 1; i <= 3; i++) {
            InheritanceJoinedTablesDiscriminatorConcreteClassA entitate = new InheritanceJoinedTablesDiscriminatorConcreteClassA();
            entitate.setName("name " + i);
            entitate.setSpecificA("specific a " + i);
            list.add(entitate);
        }
        for (; i <= 6; i++) {
            InheritanceJoinedTablesDiscriminatorConcreteClassB entitate = new InheritanceJoinedTablesDiscriminatorConcreteClassB();
            entitate.setName("name " + i);
            entitate.setSpecificB("specific b " + i);
            list.add(entitate);
        }
        for (; i <= 9; i++) {
            InheritanceJoinedTablesDiscriminatorSuperClass entitate = new InheritanceJoinedTablesDiscriminatorSuperClass();
            entitate.setName("name " + i);
            list.add(entitate);
        }
        return list;
    }

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void test() {

        List<InheritanceJoinedTablesDiscriminatorSuperClass> list = em.createQuery(
                "select t from InheritanceJoinedTablesDiscriminatorSuperClass t",
                InheritanceJoinedTablesDiscriminatorSuperClass.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model, list, ReflectionComparatorMode.LENIENT_ORDER);
    }

}

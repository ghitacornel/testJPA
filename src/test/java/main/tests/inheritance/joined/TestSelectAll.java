package main.tests.inheritance.joined;

import entities.inheritance.joined.InheritanceJoinedTablesConcreteClassA;
import entities.inheritance.joined.InheritanceJoinedTablesConcreteClassB;
import entities.inheritance.joined.InheritanceJoinedTablesSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private List<InheritanceJoinedTablesSuperClass> model = buildModel();

    static List<InheritanceJoinedTablesSuperClass> buildModel() {
        List<InheritanceJoinedTablesSuperClass> list = new ArrayList<>();
        long i;
        for (i = 1; i <= 3; i++) {
            InheritanceJoinedTablesConcreteClassA entitate = new InheritanceJoinedTablesConcreteClassA();
            entitate.setName("name " + i);
            entitate.setSpecificA("specific a " + i);
            list.add(entitate);
        }
        for (; i <= 6; i++) {
            InheritanceJoinedTablesConcreteClassB entitate = new InheritanceJoinedTablesConcreteClassB();
            entitate.setName("name " + i);
            entitate.setSpecificB("specific b " + i);
            list.add(entitate);
        }
        for (; i <= 9; i++) {
            InheritanceJoinedTablesSuperClass entitate = new InheritanceJoinedTablesSuperClass();
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

        List<InheritanceJoinedTablesSuperClass> list = em.createQuery(
                "select t from InheritanceJoinedTablesSuperClass t", InheritanceJoinedTablesSuperClass.class)
                .getResultList();

        ReflectionAssert.assertReflectionEquals(model, list, ReflectionComparatorMode.LENIENT_ORDER);

    }

}

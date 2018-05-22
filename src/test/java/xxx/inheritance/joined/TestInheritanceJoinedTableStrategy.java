package xxx.inheritance.joined;

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

public class TestInheritanceJoinedTableStrategy extends TransactionalSetup {

    private List<InheritanceJoinedTablesSuperClass> model = buildModel();

    private static List<InheritanceJoinedTablesSuperClass> buildModel() {
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
    public void testSelectAllEntitiesAsRootClass() {

        List<InheritanceJoinedTablesSuperClass> list = em.createQuery("select t from InheritanceJoinedTablesSuperClass t", InheritanceJoinedTablesSuperClass.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model, list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeAFromHierarchy() {

        List<InheritanceJoinedTablesConcreteClassA> list = em.createQuery("select t from InheritanceJoinedTablesConcreteClassA t", InheritanceJoinedTablesConcreteClassA.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(0, 3), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeBFromHierarchy() {

        List<InheritanceJoinedTablesConcreteClassB> list = em.createQuery("select t from InheritanceJoinedTablesConcreteClassB t", InheritanceJoinedTablesConcreteClassB.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(3, 6), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectSpecificEntityOfTypeAFromHierarchy() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(0).getId());
        ReflectionAssert.assertReflectionEquals(model.get(0), result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeBFromHierarchy() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(3).getId());
        ReflectionAssert.assertReflectionEquals(model.get(3), result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeParentFromHierarchy() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(6).getId());
        ReflectionAssert.assertReflectionEquals(model.get(6), result);

    }

}

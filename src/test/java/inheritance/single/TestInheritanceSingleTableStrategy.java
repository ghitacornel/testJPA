package inheritance.single;

import entities.inheritance.single.InheritanceSingleTableConcreteClassA;
import entities.inheritance.single.InheritanceSingleTableConcreteClassB;
import entities.inheritance.single.InheritanceSingleTableSuperClass;
import setup.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestInheritanceSingleTableStrategy extends TransactionalSetup {

    private List<InheritanceSingleTableSuperClass> model = buildModel();

    private static List<InheritanceSingleTableSuperClass> buildModel() {
        List<InheritanceSingleTableSuperClass> list = new ArrayList<>();
        long i;
        for (i = 1; i <= 3; i++) {
            InheritanceSingleTableConcreteClassA entity = new InheritanceSingleTableConcreteClassA();
            entity.setName("name " + i);
            entity.setSpecificA("specific a " + i);
            list.add(entity);
        }
        for (; i <= 6; i++) {
            InheritanceSingleTableConcreteClassB entity = new InheritanceSingleTableConcreteClassB();
            entity.setName("name " + i);
            entity.setSpecificB("specific b " + i);
            list.add(entity);
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

        List<InheritanceSingleTableSuperClass> list = em.createQuery("select t from InheritanceSingleTableSuperClass t", InheritanceSingleTableSuperClass.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model, list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeAFromHierarchy() {

        List<InheritanceSingleTableConcreteClassA> list = em.createQuery("select t from InheritanceSingleTableConcreteClassA t", InheritanceSingleTableConcreteClassA.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(0, 3), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeBFromHierarchy() {

        List<InheritanceSingleTableConcreteClassB> list = em.createQuery("select t from InheritanceSingleTableConcreteClassB t", InheritanceSingleTableConcreteClassB.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(3, model.size()), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectSpecificEntityOfTypeAFromHierarchy() {

        InheritanceSingleTableSuperClass result = em.find(InheritanceSingleTableSuperClass.class, model.get(0).getId());
        ReflectionAssert.assertReflectionEquals(model.get(0), result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeBFromHierarchy() {

        InheritanceSingleTableSuperClass result = em.find(InheritanceSingleTableSuperClass.class, model.get(3).getId());
        ReflectionAssert.assertReflectionEquals(model.get(3), result);

    }
}

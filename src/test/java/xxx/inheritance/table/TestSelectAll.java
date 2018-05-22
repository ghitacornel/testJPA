package xxx.inheritance.table;

import entities.inheritance.single.InheritanceSingleTableConcreteClassA;
import entities.inheritance.single.InheritanceSingleTableConcreteClassB;
import entities.inheritance.single.InheritanceSingleTableSuperClass;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private List<InheritanceSingleTableSuperClass> model = buildModel();

    static List<InheritanceSingleTableSuperClass> buildModel() {
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
    public void test() {

        List<InheritanceSingleTableSuperClass> list = em.createQuery("select t from InheritanceSingleTableSuperClass t", InheritanceSingleTableSuperClass.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model, list, ReflectionComparatorMode.LENIENT_ORDER);

    }

}

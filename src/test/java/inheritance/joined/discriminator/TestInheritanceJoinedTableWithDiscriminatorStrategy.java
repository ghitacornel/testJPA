package inheritance.joined.discriminator;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestInheritanceJoinedTableWithDiscriminatorStrategy extends TransactionalSetup {

    private List<InheritanceJoinedTablesDiscriminatorSuperClass> model = buildModel();

    private static List<InheritanceJoinedTablesDiscriminatorSuperClass> buildModel() {
        List<InheritanceJoinedTablesDiscriminatorSuperClass> list = new ArrayList<>();
        long i;
        for (i = 1; i <= 3; i++) {
            InheritanceJoinedTablesDiscriminatorConcreteClassA entity = new InheritanceJoinedTablesDiscriminatorConcreteClassA();
            entity.setName("name " + i);
            entity.setSpecificA("specific a " + i);
            list.add(entity);
        }
        for (; i <= 6; i++) {
            InheritanceJoinedTablesDiscriminatorConcreteClassB entity = new InheritanceJoinedTablesDiscriminatorConcreteClassB();
            entity.setName("name " + i);
            entity.setSpecificB("specific b " + i);
            list.add(entity);
        }
        for (; i <= 9; i++) {
            InheritanceJoinedTablesDiscriminatorSuperClass entity = new InheritanceJoinedTablesDiscriminatorSuperClass();
            entity.setName("name " + i);
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

        List<InheritanceJoinedTablesDiscriminatorSuperClass> list = em.createQuery("select t from InheritanceJoinedTablesDiscriminatorSuperClass t", InheritanceJoinedTablesDiscriminatorSuperClass.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model, list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeAFromHierarchy() {

        List<InheritanceJoinedTablesDiscriminatorConcreteClassA> list = em.createQuery("select t from InheritanceJoinedTablesDiscriminatorConcreteClassA t", InheritanceJoinedTablesDiscriminatorConcreteClassA.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(0, 3), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeBFromHierarchy() {

        List<InheritanceJoinedTablesDiscriminatorConcreteClassB> list = em.createQuery("select t from InheritanceJoinedTablesDiscriminatorConcreteClassB t", InheritanceJoinedTablesDiscriminatorConcreteClassB.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model.subList(3, 6), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectSpecificEntityOfTypeAFromHierarchy() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(0).getId());
        ReflectionAssert.assertReflectionEquals(model.get(0), result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeBFromHierarchy() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(3).getId());
        ReflectionAssert.assertReflectionEquals(model.get(3), result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeParentFromHierarchy() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(6).getId());
        ReflectionAssert.assertReflectionEquals(model.get(6), result);

    }

}

package inheritance.single;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

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

    @BeforeEach
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testSelectAllEntitiesAsRootClass() {

        List<InheritanceSingleTableSuperClass> list = em.createQuery("select t from InheritanceSingleTableSuperClass t", InheritanceSingleTableSuperClass.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeAFromHierarchy() {

        List<InheritanceSingleTableConcreteClassA> list = em.createQuery("select t from InheritanceSingleTableConcreteClassA t", InheritanceSingleTableConcreteClassA.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model.subList(0, 3)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeBFromHierarchy() {

        List<InheritanceSingleTableConcreteClassB> list = em.createQuery("select t from InheritanceSingleTableConcreteClassB t", InheritanceSingleTableConcreteClassB.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model.subList(3, model.size())).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectSpecificEntityOfTypeAFromHierarchy() {

        InheritanceSingleTableSuperClass result = em.find(InheritanceSingleTableSuperClass.class, model.get(0).getId());
        org.assertj.core.api.Assertions.assertThat(model.get(0)).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeBFromHierarchy() {

        InheritanceSingleTableSuperClass result = em.find(InheritanceSingleTableSuperClass.class, model.get(3).getId());
        org.assertj.core.api.Assertions.assertThat(model.get(3)).usingRecursiveComparison().isEqualTo(result);

    }
}

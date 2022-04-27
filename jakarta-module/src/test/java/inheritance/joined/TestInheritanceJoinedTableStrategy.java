package inheritance.joined;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

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

    @BeforeEach
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testSelectAllEntitiesAsRootClass() {

        List<InheritanceJoinedTablesSuperClass> list = em.createQuery("select t from InheritanceJoinedTablesSuperClass t", InheritanceJoinedTablesSuperClass.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeAFromHierarchy() {

        List<InheritanceJoinedTablesConcreteClassA> list = em.createQuery("select t from InheritanceJoinedTablesConcreteClassA t", InheritanceJoinedTablesConcreteClassA.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model.subList(0, 3)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeBFromHierarchy() {

        List<InheritanceJoinedTablesConcreteClassB> list = em.createQuery("select t from InheritanceJoinedTablesConcreteClassB t", InheritanceJoinedTablesConcreteClassB.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model.subList(3, 6)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectSpecificEntityOfTypeAFromHierarchy() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(0).getId());
        org.assertj.core.api.Assertions.assertThat(model.get(0)).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeBFromHierarchy() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(3).getId());
        org.assertj.core.api.Assertions.assertThat(model.get(3)).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeParentFromHierarchy() {

        InheritanceJoinedTablesSuperClass result = em.find(InheritanceJoinedTablesSuperClass.class, model.get(6).getId());
        org.assertj.core.api.Assertions.assertThat(model.get(6)).usingRecursiveComparison().isEqualTo(result);

    }

}

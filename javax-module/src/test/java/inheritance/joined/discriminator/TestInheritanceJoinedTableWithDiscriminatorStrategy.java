package inheritance.joined.discriminator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestInheritanceJoinedTableWithDiscriminatorStrategy extends TransactionalSetup {

    private final List<InheritanceJoinedTablesDiscriminatorSuperClass> model = buildModel();

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

    @BeforeEach
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testSelectAllEntitiesAsRootClass() {

        List<InheritanceJoinedTablesDiscriminatorSuperClass> list = em.createQuery("select t from InheritanceJoinedTablesDiscriminatorSuperClass t", InheritanceJoinedTablesDiscriminatorSuperClass.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeAFromHierarchy() {

        List<InheritanceJoinedTablesDiscriminatorConcreteClassA> list = em.createQuery("select t from InheritanceJoinedTablesDiscriminatorConcreteClassA t", InheritanceJoinedTablesDiscriminatorConcreteClassA.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model.subList(0, 3)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectAllSpecificEntitiesOfTypeBFromHierarchy() {

        List<InheritanceJoinedTablesDiscriminatorConcreteClassB> list = em.createQuery("select t from InheritanceJoinedTablesDiscriminatorConcreteClassB t", InheritanceJoinedTablesDiscriminatorConcreteClassB.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(model.subList(3, 6)).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(list);

    }

    @Test
    public void testSelectSpecificEntityOfTypeAFromHierarchy() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(0).getId());
        org.assertj.core.api.Assertions.assertThat(model.get(0)).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeBFromHierarchy() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(3).getId());
        org.assertj.core.api.Assertions.assertThat(model.get(3)).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    public void testSelectSpecificEntityOfTypeParentFromHierarchy() {

        InheritanceJoinedTablesDiscriminatorSuperClass result = em.find(InheritanceJoinedTablesDiscriminatorSuperClass.class, model.get(6).getId());
        org.assertj.core.api.Assertions.assertThat(model.get(6)).usingRecursiveComparison().isEqualTo(result);

    }

}

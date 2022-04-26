package queries.named.nativ;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestNativeQuery extends TransactionalSetup {

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    private List<EntityWithNamedNativeQuery> buildModel() {
        List<EntityWithNamedNativeQuery> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            EntityWithNamedNativeQuery entity = new EntityWithNamedNativeQuery();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }
        return list;
    }

    @Test
    public void testWithNamedParameters() {

        List<EntityWithNamedNativeQuery> list = em.createNamedQuery("EntityWithNamedNativeQuery.findByNameNative", EntityWithNamedNativeQuery.class).setParameter("name", "%1%").getResultList();

        List<EntityWithNamedNativeQuery> expected = new ArrayList<>();
        expected.add(buildModel().get(0));
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(list);
    }

    @Test
    public void testWithOrderParameters() {

        EntityWithNamedNativeQuery entity = em.createNamedQuery("EntityWithNamedNativeQuery.findByIdNative", EntityWithNamedNativeQuery.class).setParameter(1, 1).getSingleResult();
        org.assertj.core.api.Assertions.assertThat(buildModel().get(0)).usingRecursiveComparison().isEqualTo(entity);

    }

    @Test
    public void testNamedQueryDefinedSeparately() {

        EntityWithNamedNativeQuery entity = em.createNamedQuery("EntityWithNamedNativeQuery.findByExactNameNative", EntityWithNamedNativeQuery.class).setParameter(1, "name 2").getSingleResult();
        org.assertj.core.api.Assertions.assertThat(buildModel().get(1)).usingRecursiveComparison().isEqualTo(entity);

    }

    @Test
    public void testNamedQueryDefinedOnEntity() {

        Long count = em.createNamedQuery("EntityWithNamedNativeQuery.countAll", Long.class).getSingleResult();
        Assertions.assertEquals(5L, count.longValue());

    }

    @Test
    public void testNativeQueryReturnsManagedEntitiesWhenFullEntitiesAreFetched() {

        // fetch all
        List<EntityWithNamedNativeQuery> existingData = em.createNativeQuery("select * from EntityWithNamedNativeQuery", EntityWithNamedNativeQuery.class).getResultList();

        // test fetch all works as expected
        org.assertj.core.api.Assertions.assertThat(buildModel()).usingRecursiveComparison().isEqualTo(existingData);

        // alter fetched data
        for (EntityWithNamedNativeQuery entity : existingData) {
            entity.setName("new name " + entity.getId());
        }

        flushAndClear();

        // fetch all again
        List<EntityWithNamedNativeQuery> alteredData = em.createNativeQuery("select * from EntityWithNamedNativeQuery", EntityWithNamedNativeQuery.class).getResultList();

        // test fetched again data is the managed + changed + automatically persisted data
        List<EntityWithNamedNativeQuery> expected = buildModel();
        for (EntityWithNamedNativeQuery entity : expected) {
            entity.setName("new name " + entity.getId());
        }
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(alteredData);


    }

}

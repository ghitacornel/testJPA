package queries.named.nativ;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
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
        ReflectionAssert.assertReflectionEquals(expected, list);
    }

    @Test
    public void testWithOrderParameters() {

        EntityWithNamedNativeQuery entity = em.createNamedQuery("EntityWithNamedNativeQuery.findByIdNative", EntityWithNamedNativeQuery.class).setParameter(1, 1).getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(0), entity);

    }

    @Test
    public void testNamedQueryDefinedSeparately() {

        EntityWithNamedNativeQuery entity = em.createNamedQuery("EntityWithNamedNativeQuery.findByExactNameNative", EntityWithNamedNativeQuery.class).setParameter(1, "name 2").getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(1), entity);

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
        ReflectionAssert.assertReflectionEquals(buildModel(), existingData);

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
        ReflectionAssert.assertReflectionEquals(expected, alteredData);


    }

}

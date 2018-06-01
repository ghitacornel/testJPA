package queries.named.nativ;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestNativeQuery extends TransactionalSetup {

    @Before
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

}

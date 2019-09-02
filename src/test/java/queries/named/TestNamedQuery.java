package queries.named;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestNamedQuery extends TransactionalSetup {

    @Before
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    private List<EntityWithNamedQuery> buildModel() {
        List<EntityWithNamedQuery> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            EntityWithNamedQuery entity = new EntityWithNamedQuery();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }
        return list;
    }

    @Test
    public void testWithNamedParameters() {

        List<EntityWithNamedQuery> list = em.createNamedQuery("EntityWithNamedQuery.findByName", EntityWithNamedQuery.class).setParameter("name", "%1%").getResultList();

        List<EntityWithNamedQuery> expected = new ArrayList<>();
        expected.add(buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(expected, list);

    }

    @Test
    public void testWithOrderParameters() {

        EntityWithNamedQuery entity = em.createNamedQuery("EntityWithNamedQuery.findById", EntityWithNamedQuery.class).setParameter(1, 1).getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(0), entity);

    }

    @Test
    public void testNamedQueryDefinedSeparately() {

        EntityWithNamedQuery entity = em.createNamedQuery("EntityWithNamedQuery.findByExactName", EntityWithNamedQuery.class).setParameter("name", "name 2").getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(1), entity);

    }

    @Test
    public void testNamedQueryDefinedOnEntity() {

        Long count = em.createNamedQuery("EntityWithNamedQuery.countAll", Long.class).getSingleResult();
        Assert.assertEquals(5L, count.longValue());

    }
}

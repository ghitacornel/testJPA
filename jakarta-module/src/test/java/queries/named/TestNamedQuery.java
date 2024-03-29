package queries.named;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestNamedQuery extends TransactionalSetup {

    @BeforeEach
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
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(list);

    }

    @Test
    public void testWithOrderParameters() {

        EntityWithNamedQuery entity = em.createNamedQuery("EntityWithNamedQuery.findById", EntityWithNamedQuery.class).setParameter(1, 1).getSingleResult();
        org.assertj.core.api.Assertions.assertThat(buildModel().get(0)).usingRecursiveComparison().isEqualTo(entity);

    }

    @Test
    public void testNamedQueryDefinedSeparately() {

        EntityWithNamedQuery entity = em.createNamedQuery("EntityWithNamedQuery.findByExactName", EntityWithNamedQuery.class).setParameter("name", "name 2").getSingleResult();
        org.assertj.core.api.Assertions.assertThat(buildModel().get(1)).usingRecursiveComparison().isEqualTo(entity);

    }

    @Test
    public void testNamedQueryDefinedOnEntity() {

        Long count = em.createNamedQuery("EntityWithNamedQuery.countAll", Long.class).getSingleResult();
        Assertions.assertEquals(5L, count.longValue());

    }
}

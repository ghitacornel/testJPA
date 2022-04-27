package queries.bulk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestBulkDoesNotAffectPersistenceContext extends TransactionalSetup {

    private static List<BulkQueryEntity> buildModel() {
        List<BulkQueryEntity> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            BulkQueryEntity entity = new BulkQueryEntity();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }
        return list;
    }

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testBulkUpdateDoesNotAffectPersistenceContext() {

        // first fetch and verify
        BulkQueryEntity existing1 = em.find(BulkQueryEntity.class, 1);
        org.assertj.core.api.Assertions.assertThat(existing1).usingRecursiveComparison().isEqualTo(buildModel().get(0));

        // second issue a bulk update
        em.createQuery("update BulkQueryEntity e set e.name = 'dummy test bulk'").executeUpdate();

        // verify bulk update didn't affect already fetched entities
        org.assertj.core.api.Assertions.assertThat(existing1).usingRecursiveComparison().isEqualTo(buildModel().get(0));

        // verify bulk update won't affect newly fetched entities taken from persistence context
        BulkQueryEntity existing2 = em.find(BulkQueryEntity.class, 1);
        org.assertj.core.api.Assertions.assertThat(existing2).usingRecursiveComparison().isEqualTo(buildModel().get(0));

        // flush and clear
        flushAndClear();

        // verify bulk update will affect newly fetched entities taken from persistence context after a clear
        {
            BulkQueryEntity existing = em.find(BulkQueryEntity.class, 1);
            BulkQueryEntity expected = buildModel().get(0);
            expected.setName("dummy test bulk");
            org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(existing);
        }

        // verify bulk update didn't affect previously fetched entities again
        org.assertj.core.api.Assertions.assertThat(existing1).usingRecursiveComparison().isEqualTo(buildModel().get(0));
        org.assertj.core.api.Assertions.assertThat(existing2).usingRecursiveComparison().isEqualTo(buildModel().get(0));

        // verify bulk affected all expected entities not fetched yet
        {
            List<BulkQueryEntity> existingEntities = em.createQuery("select e from BulkQueryEntity e", BulkQueryEntity.class).getResultList();
            List<BulkQueryEntity> expectedEntities = buildModel();
            for (BulkQueryEntity entity : expectedEntities) {
                entity.setName("dummy test bulk");
            }
            org.assertj.core.api.Assertions.assertThat(expectedEntities).usingRecursiveComparison().isEqualTo(existingEntities);
        }

    }

    @Test
    public void testBulkDeleteDoesNotAffectPersistenceContext() {

        // first fetch and verify
        BulkQueryEntity existing1 = em.find(BulkQueryEntity.class, 1);
        org.assertj.core.api.Assertions.assertThat(existing1).usingRecursiveComparison().isEqualTo(buildModel().get(0));

        // second issue a bulk update
        em.createQuery("delete from BulkQueryEntity e").executeUpdate();

        // verify bulk delete didn't affect already fetched entities
        org.assertj.core.api.Assertions.assertThat(existing1).usingRecursiveComparison().isEqualTo(buildModel().get(0));

        // verify bulk delete didn't affect already fetched entities
        em.flush();
        BulkQueryEntity existing2 = em.find(BulkQueryEntity.class, 1);
        org.assertj.core.api.Assertions.assertThat(existing2).usingRecursiveComparison().isEqualTo(buildModel().get(0));

        // verify bulk delete will affect newly fetched entities
        flushAndClear();
        Assertions.assertNull(em.find(BulkQueryEntity.class, 1));

        // verify bulk delete didn't affect previously fetched entities again
        org.assertj.core.api.Assertions.assertThat(existing1).usingRecursiveComparison().isEqualTo(buildModel().get(0));
        org.assertj.core.api.Assertions.assertThat(existing2).usingRecursiveComparison().isEqualTo(buildModel().get(0));

        // verify bulk affected all expected entities not fetched yet
        Assertions.assertTrue(em.createQuery("select e from BulkQueryEntity e", BulkQueryEntity.class).getResultList().isEmpty());

    }
}

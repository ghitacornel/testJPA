package queries.bulk;

import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import queries.bulk.BulkQueryEntity;

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

    @Before
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testBulkUpdateDoesNotAffectPersistenceContext() {

        // first fetch and verify
        BulkQueryEntity existing1 = em.find(BulkQueryEntity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // second issue a bulk update
        em.createQuery("update BulkQueryEntity e set e.name = 'dummy test bulk'").executeUpdate();

        // verify bulk update didn't affect already fetched entities
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // verify bulk update won't affect newly fetched entities taken from persistence context
        BulkQueryEntity existing2 = em.find(BulkQueryEntity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // flush and clear
        flushAndClear();

        // verify bulk update will affect newly fetched entities taken from persistence context after a clear
        {
            BulkQueryEntity existing = em.find(BulkQueryEntity.class, 1);
            BulkQueryEntity expected = buildModel().get(0);
            expected.setName("dummy test bulk");
            ReflectionAssert.assertReflectionEquals(expected, existing);
        }

        // verify bulk update didn't affect previously fetched entities again
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // verify bulk affected all expected entities not fetched yet
        {
            List<BulkQueryEntity> existingEntities = em.createQuery("select e from BulkQueryEntity e", BulkQueryEntity.class).getResultList();
            List<BulkQueryEntity> expectedEntities = buildModel();
            for (BulkQueryEntity entity : expectedEntities) {
                entity.setName("dummy test bulk");
            }
            ReflectionAssert.assertReflectionEquals(expectedEntities, existingEntities, ReflectionComparatorMode.LENIENT_ORDER);
        }

    }

    @Test
    public void testBulkDeleteDoesNotAffectPersistenceContext() {

        // first fetch and verify
        BulkQueryEntity existing1 = em.find(BulkQueryEntity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // second issue a bulk update
        em.createQuery("delete from BulkQueryEntity e").executeUpdate();

        // verify bulk delete didn't affect already fetched entities
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // verify bulk delete didn't affect already fetched entities
        em.flush();
        BulkQueryEntity existing2 = em.find(BulkQueryEntity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // verify bulk delete will affect newly fetched entities
        flushAndClear();
        Assert.assertNull(em.find(BulkQueryEntity.class, 1));

        // verify bulk delete didn't affect previously fetched entities again
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // verify bulk affected all expected entities not fetched yet
        Assert.assertTrue(em.createQuery("select e from BulkQueryEntity e", BulkQueryEntity.class).getResultList().isEmpty());

    }
}

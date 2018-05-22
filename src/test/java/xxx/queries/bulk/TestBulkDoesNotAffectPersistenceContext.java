package xxx.queries.bulk;

import entities.simple.Entity;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestBulkDoesNotAffectPersistenceContext extends TransactionalSetup {

    private static List<Entity> buildModel() {
        List<Entity> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Entity entity = new Entity();
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
        Entity existing1 = em.find(Entity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // second issue a bulk update
        em.createQuery("update Entity e set e.name = 'dummy test bulk'").executeUpdate();

        // verify bulk update didn't affect already fetched entities
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // verify bulk update won't affect newly fetched entities taken from persistence context
        Entity existing2 = em.find(Entity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // flush and clear
        flushAndClear();

        // verify bulk update will affect newly fetched entities taken from persistence context after a clear
        {
            Entity existing = em.find(Entity.class, 1);
            Entity expected = buildModel().get(0);
            expected.setName("dummy test bulk");
            ReflectionAssert.assertReflectionEquals(expected, existing);
        }

        // verify bulk update didn't affect previously fetched entities again
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // verify bulk affected all expected entities not fetched yet
        {
            List<Entity> existingEntities = em.createQuery("select e from Entity e", Entity.class).getResultList();
            List<Entity> expectedEntities = buildModel();
            for (Entity entity : expectedEntities) {
                entity.setName("dummy test bulk");
            }
            ReflectionAssert.assertReflectionEquals(expectedEntities, existingEntities, ReflectionComparatorMode.LENIENT_ORDER);
        }

    }

    @Test
    public void testBulkDeleteDoesNotAffectPersistenceContext() {

        // first fetch and verify
        Entity existing1 = em.find(Entity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // second issue a bulk update
        em.createQuery("delete from Entity e").executeUpdate();

        // verify bulk delete didn't affect already fetched entities
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // verify bulk delete didn't affect already fetched entities
        em.flush();
        Entity existing2 = em.find(Entity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // verify bulk delete will affect newly fetched entities
        flushAndClear();
        Assert.assertNull(em.find(Entity.class, 1));

        // verify bulk delete didn't affect previously fetched entities again
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // verify bulk affected all expected entities not fetched yet
        Assert.assertTrue(em.createQuery("select e from Entity e", Entity.class).getResultList().isEmpty());

    }
}

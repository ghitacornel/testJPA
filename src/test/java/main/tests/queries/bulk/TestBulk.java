package main.tests.queries.bulk;

import entities.simple.Entity;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestBulk extends TransactionalSetup {

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
    public void testBulk() {

        // first fetch and verify
        Entity existing1 = em.find(Entity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));

        // second issue a bulk update
        em.createQuery("update Entity e set e.name = 'dummy test bulk'").executeUpdate();

        // verify bulk update didn't affect already fetched entities
        Entity existing2 = em.find(Entity.class, 1);
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // flush and clear
        flushAndClear();

        // verify bulk update didn't affect already fetched entities again
        ReflectionAssert.assertReflectionEquals(existing1, buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(existing2, buildModel().get(0));

        // verify bulk affected all expected entities not fetched yet
        List<Entity> fetchedEntities = em.createQuery("select e from Entity e", Entity.class).getResultList();
        List<Entity> expectedEntities = buildModel();
        for (Entity entity : expectedEntities) {
            entity.setName("dummy test bulk");
        }
        ReflectionAssert.assertReflectionEquals(expectedEntities, fetchedEntities, ReflectionComparatorMode.LENIENT_ORDER);

    }
}

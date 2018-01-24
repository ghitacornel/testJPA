package main.tests.simple;

import entities.simple.Entity;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.ArrayList;
import java.util.List;

public class TestBulkUpdateAll extends TransactionalSetup {

    @Before
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    private List<Entity> buildModel() {
        List<Entity> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Entity entity = new Entity();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }
        return list;
    }

    @Test
    public void test() {

        // update
        em.createQuery("update Entity t set t.name=concat(t.name,t.name)").executeUpdate();
        flushAndClear();

        // fetch
        List<Entity> list = em.createQuery("select t from Entity t", Entity.class).getResultList();

        // verify, order is ignored
        List<Entity> model = buildModel();
        for (Entity entity : model) {
            entity.setName(entity.getName() + entity.getName());
        }
        ReflectionAssert.assertReflectionEquals(model, list, ReflectionComparatorMode.LENIENT_ORDER);

    }

}

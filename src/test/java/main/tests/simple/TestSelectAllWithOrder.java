package main.tests.simple;

import entities.simple.Entity;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.List;

public class TestSelectAllWithOrder extends TransactionalSetup {

    @Before
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    private List<Entity> buildModel() {
        List<Entity> list = new ArrayList<>();

        // order of identifiers is important
        for (int i = 5; i >= 1; i--) {
            Entity entity = new Entity();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }

        return list;
    }

    @Test
    public void test() {

        // fetch
        List<Entity> list = em.createQuery("select t from Entity t order by t.id desc", Entity.class)
                .getResultList();

        // verify, order is important
        ReflectionAssert.assertReflectionEquals(buildModel(), list);

    }

}

package main.tests.queries;

import entities.simple.Entity;
import entities.simple.SimpleEnum;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.List;

public class TestQueryAdvanced extends TransactionalSetup {

    List<Entity> model = buildModel();

    private static List<Entity> buildModel() {
        List<Entity> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Entity entity = new Entity();
            entity.setId(i);
            entity.setName("name " + i);
            if (i > 3) {
                entity.setEnum1(SimpleEnum.ONE);
            }
            list.add(entity);
        }
        return list;
    }

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    @Test
    public void testWithNotNullParameter() {

        List<Entity> list = em
                .createQuery("select e from Entity e where e.enum1 = :enum1",
                        Entity.class).setParameter("enum1", SimpleEnum.ONE)
                .getResultList();

        List<Entity> expected = new ArrayList<>();
        expected.add(model.get(3));
        expected.add(model.get(4));
        ReflectionAssert.assertReflectionEquals(expected, list);
    }

    @Test
    public void testWithNullParameter() {

        List<Entity> list1 = em
                .createQuery("select e from Entity e where e.enum1 = :enum1",
                        Entity.class).setParameter("enum1", null)
                .getResultList();

        ReflectionAssert.assertReflectionEquals(new ArrayList<>(), list1);

        List<Entity> list2 = em
                .createQuery("select e from Entity e where e.enum1 IS NULL",
                        Entity.class).getResultList();

        List<Entity> expected = new ArrayList<>();
        expected.add(model.get(0));
        expected.add(model.get(1));
        expected.add(model.get(2));
        ReflectionAssert.assertReflectionEquals(expected, list2);
    }

}

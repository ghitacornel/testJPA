package queries.simple;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestOrder extends TransactionalSetup {

    @Test
    public void testSelectAllWithOrderByName() {

        List<SimpleQueryEntity> model = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(6);
            model.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(5);
            model.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(3);
            entity.setName("name 3");
            entity.setValue(4);
            model.add(entity);
        }
        persist(model);
        flushAndClear();

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e order by e.id desc", SimpleQueryEntity.class).getResultList();

        Collections.reverse(model);
        ReflectionAssert.assertReflectionEquals(model, actual);

    }

    @Test
    public void testSelectAllWithOrderByNumber() {

        List<SimpleQueryEntity> model = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(6);
            model.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(5);
            model.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(3);
            entity.setName("name 3");
            entity.setValue(4);
            model.add(entity);
        }
        persist(model);
        flushAndClear();

        List<Object[]> actual = em.createQuery("select e.id, e.name, e.value from SQE e order by 3").getResultList();
        Collections.reverse(model);
        Assert.assertEquals(model.size(), actual.size());
        for (int i = 0; i < model.size(); i++) {
            Assert.assertEquals(actual.get(i)[0],model.get(i).getId());
            Assert.assertEquals(actual.get(i)[1],model.get(i).getName());
            Assert.assertEquals(actual.get(i)[2],model.get(i).getValue());
        }

    }

    @Test
    public void testSelectAllWithOrderNullsFirst() {

        List<SimpleQueryEntity> model = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(6);
            model.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(5);
            model.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(3);
            entity.setName("name 3");
            entity.setValue(null);
            model.add(entity);
        }
        persist(model);
        flushAndClear();

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e order by value nulls first", SimpleQueryEntity.class).getResultList();

        Collections.reverse(model);
        ReflectionAssert.assertReflectionEquals(model, actual);

    }

    @Test
    public void testSelectAllWithOrderNullsLast() {

        List<SimpleQueryEntity> model = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(null);
            model.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(2);
            model.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(3);
            entity.setName("name 3");
            entity.setValue(1);
            model.add(entity);
        }
        persist(model);
        flushAndClear();

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e order by value nulls last", SimpleQueryEntity.class).getResultList();

        Collections.reverse(model);
        ReflectionAssert.assertReflectionEquals(model, actual);

    }

}

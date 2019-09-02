package queries.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestDistinct extends TransactionalSetup {

    private static List<SimpleQueryEntity> buildModel() {
        List<SimpleQueryEntity> list = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(1);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(2);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(3);
            entity.setName("name 2");
            entity.setValue(3);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(4);
            entity.setName("name 3");
            entity.setValue(1);
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
    public void testSelectDistinct() {
        List<String> actual = em.createQuery("select distinct e.name from SQE e order by 1", String.class).getResultList();
        List<String> expected = new ArrayList<>();
        expected.add("name 1");
        expected.add("name 2");
        expected.add("name 3");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSelectCountDistinct() {
        Long count = em.createQuery("select count(distinct e.name) from SQE e", Long.class).getSingleResult();
        Assert.assertEquals(3, count.longValue());
    }
}

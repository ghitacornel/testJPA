package queries.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestCOUNT extends TransactionalSetup {

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
            entity.setName("name 3");
            entity.setValue(3);
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
    public void testCountAll() {
        Long count = em.createQuery("select count(*) from SQE", Long.class).getSingleResult();
        Assert.assertEquals(buildModel().size(), count.longValue());
    }

    @Test
    public void testCountSome() {
        Long count = em.createQuery("select count(e) from SQE e where e.value = 2 or e.value = 3", Long.class).getSingleResult();
        Assert.assertEquals(2, count.longValue());
    }

}

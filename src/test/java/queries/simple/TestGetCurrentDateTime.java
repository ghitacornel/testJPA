package queries.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.List;

public class TestGetCurrentDateTime extends TransactionalSetup {

    @Before
    public void before() {
        SimpleQueryEntity entity = new SimpleQueryEntity();
        entity.setId(3);
        entity.setName("name 3");
        entity.setValue(3);
        persist(entity);
        flushAndClear();
    }

    @Test
    public void testGetCurrentAll() {

        List<Object[]> result = em.createQuery("select current_date,current_time,current_timestamp from SQE").getResultList();
        Assert.assertEquals(1, result.size());
        Assert.assertNotNull(result.get(0)[0]);
        Assert.assertNotNull(result.get(0)[1]);
        Assert.assertNotNull(result.get(0)[2]);

        System.out.println(result.get(0)[0]);
        System.out.println(result.get(0)[1]);
        System.out.println(result.get(0)[2]);
    }

}

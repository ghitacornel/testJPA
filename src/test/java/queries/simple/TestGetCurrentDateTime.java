package queries.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

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
        Object current_timestamp = em.createQuery("select current_timestamp from SQE").getSingleResult();
        Assert.assertNotNull(current_timestamp);
        Object current_date = em.createQuery("select current_date from SQE").getSingleResult();
        Assert.assertNotNull(current_date);
        Object current_time = em.createQuery("select current_time from SQE").getSingleResult();
        Assert.assertNotNull(current_time);
    }

}

package queries.simple;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.Date;

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
    public void testGetCurrentTimestamp() {
        Date date = em.createQuery("select CURRENT_TIMESTAMP from SQE", Date.class).getSingleResult();
        Assert.assertNotNull(date);
    }

    @Test
    public void testGetCurrentDate() {
        Date date = em.createQuery("select CURRENT_DATE from SQE", Date.class).getSingleResult();
        Assert.assertNotNull(date);
    }

    @Test
    public void testGetCurrentTime() {
        Date date = em.createQuery("select CURRENT_TIMESTAMP from SQE", Date.class).getSingleResult();
        Assert.assertNotNull(date);
    }

}

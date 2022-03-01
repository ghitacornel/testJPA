package queries.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.Date;

public class TestGetCurrentDateTime extends TransactionalSetup {

    @BeforeEach
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
        Assertions.assertNotNull(date);
    }

    @Test
    public void testGetCurrentDate() {
        Date date = em.createQuery("select CURRENT_DATE from SQE", Date.class).getSingleResult();
        Assertions.assertNotNull(date);
    }

    @Test
    public void testGetCurrentTime() {
        Date date = em.createQuery("select CURRENT_TIMESTAMP from SQE", Date.class).getSingleResult();
        Assertions.assertNotNull(date);
    }

}

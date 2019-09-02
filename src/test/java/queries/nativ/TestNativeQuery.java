package queries.nativ;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import queries.simple.SimpleQueryEntity;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestNativeQuery extends TransactionalSetup {

    private static List<SimpleQueryEntity> buildModel() {
        List<SimpleQueryEntity> list = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(6);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(5);
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
    public void testSelectAllNameAndValue() {

        List<Object[]> data = em.createNativeQuery("select name,value from SQE where id = :id").setParameter("id", 1).getResultList();
        Assert.assertEquals(1, data.size());
        Assert.assertEquals(2, data.get(0).length);
        Assert.assertEquals("name 1", data.get(0)[0]);
        Assert.assertEquals(6, data.get(0)[1]);

    }

}

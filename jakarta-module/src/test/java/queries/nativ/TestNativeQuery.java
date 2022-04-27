package queries.nativ;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testSelectAllNameAndValue() {

        List<Object[]> data = em.createNativeQuery("select name,value from SQE where id = :id").setParameter("id", 1).getResultList();
        Assertions.assertEquals(1, data.size());
        Assertions.assertEquals(2, data.get(0).length);
        Assertions.assertEquals("name 1", data.get(0)[0]);
        Assertions.assertEquals(6, data.get(0)[1]);

    }

}

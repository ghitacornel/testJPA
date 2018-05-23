package queries.bulk;

import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import queries.bulk.BulkQueryEntity;

import java.util.ArrayList;
import java.util.List;

public class TestBulkRemoveAll extends TransactionalSetup {

    @Before
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    private List<BulkQueryEntity> buildModel() {
        List<BulkQueryEntity> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            BulkQueryEntity entity = new BulkQueryEntity();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }
        return list;
    }

    @Test
    public void test() {

        em.createQuery("delete from BulkQueryEntity").executeUpdate();
        flushAndClear();

        Assert.assertTrue(em.createQuery("select t from BulkQueryEntity t").getResultList().isEmpty());

    }

}

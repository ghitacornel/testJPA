package queries.bulk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestBulkRemoveAll extends TransactionalSetup {

    @BeforeEach
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

        Assertions.assertTrue(em.createQuery("select t from BulkQueryEntity t").getResultList().isEmpty());

    }

}

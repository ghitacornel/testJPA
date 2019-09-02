package queries.bulk;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestBulkSelectAndInsert extends TransactionalSetup {

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
            entity.setValue(i * i);
            list.add(entity);
        }
        return list;
    }

    private List<BulkTarget> buildExpectedModel() {
        List<BulkTarget> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            BulkTarget entity = new BulkTarget();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }
        return list;
    }

    @Test
    public void testSelectAndInsert() {

        // select and insert
        em.createQuery("insert into BulkTarget(id,name) select t.id, t.name from BulkQueryEntity t").executeUpdate();
        flushAndClear();

        // verify
        ReflectionAssert.assertReflectionEquals(buildExpectedModel(), em.createQuery("select t from BulkTarget t", BulkTarget.class).getResultList(), ReflectionComparatorMode.LENIENT_ORDER);

    }

}

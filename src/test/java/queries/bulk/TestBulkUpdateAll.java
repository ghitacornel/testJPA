package queries.bulk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestBulkUpdateAll extends TransactionalSetup {

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

        // update
        em.createQuery("update BulkQueryEntity t set t.name=concat(t.name,t.name)").executeUpdate();
        flushAndClear();

        // fetch
        List<BulkQueryEntity> list = em.createQuery("select t from BulkQueryEntity t", BulkQueryEntity.class).getResultList();

        // verify, order is ignored
        List<BulkQueryEntity> model = buildModel();
        for (BulkQueryEntity entity : model) {
            entity.setName(entity.getName() + entity.getName());
        }
        ReflectionAssert.assertReflectionEquals(model, list, ReflectionComparatorMode.LENIENT_ORDER);

    }

}

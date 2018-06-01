package queries.simple;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestFindById extends TransactionalSetup {

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
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(3);
            entity.setName("name 3");
            entity.setValue(4);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(4);
            entity.setName("name 4");
            entity.setValue(3);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(5);
            entity.setName("name 5");
            entity.setValue(2);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(6);
            entity.setName("name 6");
            entity.setValue(null);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(7);
            entity.setName("jOHn");
            entity.setValue(0);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(8);
            entity.setName("a john 1");
            entity.setValue(1);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(9);
            entity.setName("a second JOHN 2");
            entity.setValue(2);
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
    public void testFindByIdNoQuery() {

        SimpleQueryEntity entity = em.find(SimpleQueryEntity.class, 5);
        ReflectionAssert.assertReflectionEquals(buildModel().get(4), entity);

    }

}

package queries.simple;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestExpectUniqueResult extends TransactionalSetup {

    private static List<SimpleQueryEntity> buildModel() {
        List<SimpleQueryEntity> list = new ArrayList<>();
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(1);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(2);
            list.add(entity);
        }
        {
            SimpleQueryEntity entity = new SimpleQueryEntity();
            entity.setId(3);
            entity.setName("name 3");
            entity.setValue(3);
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
    public void testUniqueResult() {
        SimpleQueryEntity entity = em.createQuery("select e from SQE e where e.value = 1", SimpleQueryEntity.class).getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(0), entity);
    }

    @Test(expected = javax.persistence.NonUniqueResultException.class)
    public void testUniqueResultNonUniqueSelect() {
        em.createQuery("select e from SQE e where e.value = 2 or e.value = 3", SimpleQueryEntity.class).getSingleResult();
    }

    @Test(expected = javax.persistence.NoResultException.class)
    public void testUniqueResultNoData() {
        em.createQuery("select e from SQE e where e.value = 4", SimpleQueryEntity.class).getSingleResult();
    }

}

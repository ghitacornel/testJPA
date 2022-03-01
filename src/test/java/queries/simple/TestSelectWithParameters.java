package queries.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestSelectWithParameters extends TransactionalSetup {

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
        return list;
    }

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testSelectAllWithNamedParameter() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.name = :name", SimpleQueryEntity.class).setParameter("name", "name 1").getResultList();

        List<SimpleQueryEntity> expected = new ArrayList<>();
        expected.add(buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(expected, list);

    }

    @Test
    public void testSelectAllWithNamedParameters() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.name = :name and e.value = :value", SimpleQueryEntity.class).setParameter("name", "name 2").setParameter("value", 5).getResultList();

        List<SimpleQueryEntity> expected = new ArrayList<>();
        expected.add(buildModel().get(1));
        ReflectionAssert.assertReflectionEquals(expected, list);

    }

    @Test
    public void testSelectAllWithPositionParameter() {

        SimpleQueryEntity entity = em.createQuery("select e from SQE e where e.name = ?1", SimpleQueryEntity.class).setParameter(1, "name 3").getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(2), entity);

    }

    @Test
    public void testSelectAllWithPositionParameters() {

        SimpleQueryEntity entity = em.createQuery("select e from SQE e where e.value = ?2 and e.name = ?1", SimpleQueryEntity.class).setParameter(1, "name 4").setParameter(2,3).getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(3), entity);

    }

}

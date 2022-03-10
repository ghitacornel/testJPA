package queries.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testUniqueResult() {
        SimpleQueryEntity entity = em.createQuery("select e from SQE e where e.value = 1", SimpleQueryEntity.class).getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(0), entity);
    }

    @Test
    public void testUniqueResultNonUniqueSelect() {
        Assertions.assertThrows(NonUniqueResultException.class, () -> em.createQuery("select e from SQE e where e.value = 2 or e.value = 3", SimpleQueryEntity.class).getSingleResult());
    }

    @Test
    public void testUniqueResultNoData() {
        Assertions.assertThrows(NoResultException.class, () -> em.createQuery("select e from SQE e where e.value = 4", SimpleQueryEntity.class).getSingleResult());
    }

}

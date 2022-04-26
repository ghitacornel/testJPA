package queries.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSimpleQueries extends TransactionalSetup {

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

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testSelectAll() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e", SimpleQueryEntity.class).getResultList();
        org.assertj.core.api.Assertions.assertThat(buildModel()).usingRecursiveComparison().isEqualTo(list);

    }

    @Test
    public void testSelectAllWithBoundaries() {

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e order by id desc", SimpleQueryEntity.class).setFirstResult(1).setMaxResults(3).getResultList();

        // verify, order is important
        List<SimpleQueryEntity> expected = buildModel();
        Collections.reverse(expected);
        expected = expected.subList(1, 4);
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(actual);

    }

    @Test
    public void testSelectAllWithBETWEEN() {

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e where id between 1 and 4 order by id", SimpleQueryEntity.class).getResultList();

        // verify, order is important
        List<SimpleQueryEntity> expected = buildModel();
        expected = expected.subList(0, 4);
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(actual);

    }

    @Test
    public void testSelectAllWithSimpleComparison() {

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e where id >= 1 and id <= 4 order by id", SimpleQueryEntity.class).getResultList();

        // verify, order is important
        List<SimpleQueryEntity> expected = buildModel();
        expected = expected.subList(0, 4);
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(actual);

    }
}

package queries.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        return list;
    }

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testFindByIdExistingId() {

        SimpleQueryEntity entity = em.find(SimpleQueryEntity.class, 1);
        org.assertj.core.api.Assertions.assertThat(buildModel().get(0)).usingRecursiveComparison().isEqualTo(entity);

    }

    @Test
    public void testFindByIdNotExistingId() {

        SimpleQueryEntity entity = em.find(SimpleQueryEntity.class, -1);
        Assertions.assertNull(entity);

    }

}

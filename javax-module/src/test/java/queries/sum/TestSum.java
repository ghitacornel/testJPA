package queries.sum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestSum extends TransactionalSetup {

    private static List<EntityForSum> buildModel() {
        List<EntityForSum> list = new ArrayList<>();
        {
            EntityForSum entity = new EntityForSum();
            entity.setId(1);
            entity.setAge(1);
            entity.setSalary(11);
            list.add(entity);
        }
        {
            EntityForSum entity = new EntityForSum();
            entity.setId(2);
            entity.setAge(2);
            entity.setSalary(22);
            list.add(entity);
        }
        {
            EntityForSum entity = new EntityForSum();
            entity.setId(3);
            entity.setAge(null);
            entity.setSalary(33);
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
    public void testSum() {
        Long sum = em.createQuery("select sum(t.salary) from queries.sum.EntityForSum t", Long.class).getSingleResult();
        Assertions.assertEquals(66, sum);
    }

    @Test
    public void testSumWithNull() {
        // COALESCE NOT NEEDED
        Long sum = em.createQuery("select sum(t.age) from queries.sum.EntityForSum t", Long.class).getSingleResult();
        Assertions.assertEquals(3, sum);
    }

    @Test
    public void testSumWithNullWithCoalesce() {
        // COALESCE NOT NEEDED
        Long sum = em.createQuery("select sum(coalesce(t.age,0)) from queries.sum.EntityForSum t", Long.class).getSingleResult();
        Assertions.assertEquals(3, sum);
    }

}

package queries.simple;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestFindByUsingIN extends TransactionalSetup {

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

    /**
     * TODO always check for null or empty collections passed as parameters
     * TODO passing null or empty collections as parameters can cause generation of corrupted SQLs having empty IN clauses
     * TODO some JPA implementations produce corrupted SQLs, or some databases accept such SQLs and produce no results
     * TODO make sure the database IN SQL clause does not have a limit of allowed IN values
     * sometimes a check for null or empty collection parameters avoid an SQL execution
     */
    @Test
    public void testWithCollectionParameter() {

        List<String> parameter = new ArrayList<>();
        parameter.add("name 1");
        parameter.add("name 2");

        List<SimpleQueryEntity> actual = em.createQuery("select e from SQE e where e.name IN :names", SimpleQueryEntity.class).setParameter("names", parameter).getResultList();
        ReflectionAssert.assertReflectionEquals(buildModel().subList(0, 2), actual);

    }

}

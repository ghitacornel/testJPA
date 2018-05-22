package xxx.queries.simple;

import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import queries.simple.SimpleQueryEntity;
import queries.simple.SimpleQueryEntity_;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TestQuery extends TransactionalSetup {

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
            entity.setValue(1);
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
    public void testSelectAll() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e", SimpleQueryEntity.class).getResultList();
        ReflectionAssert.assertReflectionEquals(buildModel(), list, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testSelectAllWithNamedParameters() {

        List<SimpleQueryEntity> list = em.createQuery("select e from SQE e where e.name = :name", SimpleQueryEntity.class).setParameter("name", "name 1").getResultList();

        List<SimpleQueryEntity> expected = new ArrayList<>();
        expected.add(buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(expected, list);

    }

    @Test
    public void testSelectAllWithOrderParameters() {

        SimpleQueryEntity entity = em.createQuery("select e from SQE e where e.name = ?1", SimpleQueryEntity.class).setParameter(1, "name 2").getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(1), entity);

    }

    @Test
    public void testFindByNameWithCriteriaBuilderNoMetamodel() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<SimpleQueryEntity> query = criteriaBuilder.createQuery(SimpleQueryEntity.class);
        Root<SimpleQueryEntity> from = query.from(SimpleQueryEntity.class);
        query.where(criteriaBuilder.equal(from.get("name"), "name 3"));
        TypedQuery<SimpleQueryEntity> typedQuery = em.createQuery(query);

        SimpleQueryEntity entity = typedQuery.getSingleResult();

        ReflectionAssert.assertReflectionEquals(buildModel().get(2), entity);
    }

    @Test
    public void testFindByNameWithCriteriaBuilderMetamodel() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<SimpleQueryEntity> query = criteriaBuilder.createQuery(SimpleQueryEntity.class);
        Root<SimpleQueryEntity> from = query.from(SimpleQueryEntity.class);
        query.where(criteriaBuilder.equal(from.get(SimpleQueryEntity_.name), "name 4"));
        TypedQuery<SimpleQueryEntity> typedQuery = em.createQuery(query);

        SimpleQueryEntity entity = typedQuery.getSingleResult();

        ReflectionAssert.assertReflectionEquals(buildModel().get(3), entity);
    }

    @Test
    public void testFindByIdNoQuery() {

        SimpleQueryEntity entity = em.find(SimpleQueryEntity.class, 5);
        ReflectionAssert.assertReflectionEquals(buildModel().get(4), entity);

    }
}

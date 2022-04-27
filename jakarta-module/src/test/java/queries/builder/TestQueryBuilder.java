package queries.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import queries.simple.SimpleQueryEntity;
//import queries.simple.SimpleQueryEntity_;
import setup.TransactionalSetup;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TestQueryBuilder extends TransactionalSetup {

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
        return list;
    }

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testFindByNameWithCriteriaBuilderNoMetaModel() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<SimpleQueryEntity> query = criteriaBuilder.createQuery(SimpleQueryEntity.class);
        Root<SimpleQueryEntity> from = query.from(SimpleQueryEntity.class);
        query.where(criteriaBuilder.equal(from.get("name"), "name 3"));
        TypedQuery<SimpleQueryEntity> typedQuery = em.createQuery(query);

        SimpleQueryEntity entity = typedQuery.getSingleResult();

        org.assertj.core.api.Assertions.assertThat(buildModel().get(2)).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    public void testFindByNameWithCriteriaBuilderMetaModel() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<SimpleQueryEntity> query = criteriaBuilder.createQuery(SimpleQueryEntity.class);
        Root<SimpleQueryEntity> from = query.from(SimpleQueryEntity.class);
//        query.where(criteriaBuilder.equal(from.get(SimpleQueryEntity_.name), "name 4"));
        TypedQuery<SimpleQueryEntity> typedQuery = em.createQuery(query);

        SimpleQueryEntity entity = typedQuery.getSingleResult();

        org.assertj.core.api.Assertions.assertThat(buildModel().get(3)).usingRecursiveComparison().isEqualTo(entity);
    }

}

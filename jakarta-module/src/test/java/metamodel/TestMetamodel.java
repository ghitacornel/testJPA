package metamodel;

import entities.simple.Entity;
//import entities.simple.Entity_;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class TestMetamodel extends TransactionalSetup {

    @Test
    public void testFindByName() {

        // create new entity
        Entity initialEntity = new Entity();
        initialEntity.setId(1);
        initialEntity.setName("name");
        em.persist(initialEntity);
        flushAndClear();

        // find by
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> cq = cb.createQuery(Entity.class);
        Root<Entity> from = cq.from(Entity.class);
//        cq.where(cb.equal(from.get(Entity_.name), "name"));

        Entity actualData = em.createQuery(cq).getSingleResult();

        org.assertj.core.api.Assertions.assertThat(initialEntity).usingRecursiveComparison().isEqualTo(actualData);

    }
}

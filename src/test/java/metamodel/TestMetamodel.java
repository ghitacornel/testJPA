package metamodel;

import entities.simple.Entity;
import entities.simple.Entity_;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        cq.where(cb.equal(from.get(Entity_.name), "name"));

        Entity actualData = em.createQuery(cq).getSingleResult();

        ReflectionAssert.assertReflectionEquals(initialEntity, actualData);

    }
}

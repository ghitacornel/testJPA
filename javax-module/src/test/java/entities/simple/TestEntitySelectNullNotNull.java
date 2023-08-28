package entities.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestEntitySelectNullNotNull extends TransactionalSetup {

    @BeforeEach
    public void verifyDatabaseState() {
        verifyCorrespondingTableIsEmpty(Entity.class);
    }

    @Test
    public void testCreateReadUpdateReadRemoveRead() {

        // create new entity
        {
            Entity entity = new Entity();
            entity.setId(1);
            entity.setName("name 1");
            entity.setValue(1);
            em.persist(entity);
            flushAndClear();
        }
        {
            Entity entity = new Entity();
            entity.setId(2);
            entity.setName("name 2");
            entity.setValue(null);
            em.persist(entity);
            flushAndClear();
        }

        // check with simple conditions
        {
            Entity entity = em.createQuery("select e from entities.simple.Entity e where e.value = :value", Entity.class)
                    .setParameter("value", 1)
                    .getSingleResult();
            Assertions.assertEquals(1, entity.getId());
        }
        {
            Entity entity = em.createQuery("select e from entities.simple.Entity e where e.value is null", Entity.class)
                    .getSingleResult();
            Assertions.assertEquals(2, entity.getId());
        }

        // check with both conditions
        {
            String query = "select e from entities.simple.Entity e where (:value is not null and e.value = :value) or (:value is null and e.value is null)";
            Entity entity1 = em.createQuery(query, Entity.class)
                    .setParameter("value", 1)
                    .getSingleResult();
            Assertions.assertEquals(1, entity1.getId());
            Entity entity2 = em.createQuery(query, Entity.class)
                    .setParameter("value", null)
                    .getSingleResult();
            Assertions.assertEquals(2, entity2.getId());
        }

    }

}

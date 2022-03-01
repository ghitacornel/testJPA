package entities.projection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestEntityWithProjection extends TransactionalSetup {

    EntityWithProjection entity;

    @BeforeEach
    public void ensureSomeDataIsPresent() {
        entity = new EntityWithProjection(1, "name", 2);
        em.persist(entity);
        flushAndClear();
    }

    @Test
    public void testProjection() {

        Projection1 projection1 = em.createQuery("select new entities.projection.Projection1(e.id,e.name) from EntityWithProjection e where e.id = 1", Projection1.class).getSingleResult();
        Assertions.assertEquals(entity.getId(), projection1.getId());
        Assertions.assertEquals(entity.getName(), projection1.getName());

        Projection2 projection2 = em.createQuery("select new entities.projection.Projection2(e.id,e.value) from EntityWithProjection e where e.id = 1", Projection2.class).getSingleResult();
        Assertions.assertEquals(entity.getId(), projection2.id);
        Assertions.assertEquals(entity.getValue(), projection2.value);

        ProjectionFull projectionFull = em.createQuery("select new entities.projection.ProjectionFull(e.id,e.name,e.value) from EntityWithProjection e where e.id = 1", ProjectionFull.class).getSingleResult();
        Assertions.assertEquals(entity.getId(), projectionFull.getId());
        Assertions.assertEquals(entity.getName(), projectionFull.getName());
        Assertions.assertEquals(entity.getValue(), projectionFull.getValue());

        // check that this search will execute a second "find by id query"
        // loading fully an entity instance through a projection does not load and keep the entity instance in persistence context
        em.find(EntityWithProjection.class, 1);

    }

    @Test
    public void testProjectionFullLoadDoesNotLoadTheEntityAlso() {

        Assertions.assertFalse(em.contains(entity));

        ProjectionFull projectionFull = em.createQuery("select new entities.projection.ProjectionFull(e.id,e.name,e.value) from EntityWithProjection e where e.id = 1", ProjectionFull.class).getSingleResult();

        Assertions.assertFalse(em.contains(entity));

        // check that this search will execute a second "find by id query"
        // loading fully an entity instance through a projection does not load and keep the entity instance in persistence context
        em.find(EntityWithProjection.class, 1);

    }

}


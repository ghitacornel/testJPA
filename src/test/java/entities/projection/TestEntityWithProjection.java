package entities.projection;

import entities.projection.EntityWithProjection;
import entities.projection.Projection1OfEntityWithProjection;
import entities.projection.Projection2OfEntityWithProjection;
import entities.projection.ProjectionFullOfEntityWithProjection;
import org.junit.Assert;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestEntityWithProjection extends TransactionalSetup {

    @Test
    public void testProjection() {

        EntityWithProjection entity = new EntityWithProjection();
        entity.setId(1);
        entity.setName("name");
        entity.setValue(2);
        em.persist(entity);
        flushAndClear();

        Projection1OfEntityWithProjection projection1 = em.createQuery("select new entities.projection.Projection1OfEntityWithProjection(e.id,e.name) from EntityWithProjection e where e.id = 1", Projection1OfEntityWithProjection.class).getSingleResult();
        Assert.assertEquals(entity.getId(), projection1.getId());
        Assert.assertEquals(entity.getName(), projection1.getName());

        Projection2OfEntityWithProjection projection2 = em.createQuery("select new entities.projection.Projection2OfEntityWithProjection(e.id,e.value) from EntityWithProjection e where e.id = 1", Projection2OfEntityWithProjection.class).getSingleResult();
        Assert.assertEquals(entity.getId(), projection2.getId());
        Assert.assertEquals(entity.getValue(), projection2.getValue());

        ProjectionFullOfEntityWithProjection projectionFull = em.createQuery("select new entities.projection.ProjectionFullOfEntityWithProjection(e.id,e.name,e.value) from EntityWithProjection e where e.id = 1", ProjectionFullOfEntityWithProjection.class).getSingleResult();
        Assert.assertEquals(entity.getId(), projectionFull.getId());
        Assert.assertEquals(entity.getName(), projectionFull.getName());
        Assert.assertEquals(entity.getValue(), projectionFull.getValue());

        // check that this search will execute a second "find by id query"
        // loading fully an entity instance through a projection does not load and keep the entity instance in persistence context
        em.find(EntityWithProjection.class, 1);

    }

}


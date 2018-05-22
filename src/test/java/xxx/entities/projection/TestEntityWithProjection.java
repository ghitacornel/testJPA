package xxx.entities.projection;

import entities.simple.projection.EntityWithProjection;
import entities.simple.projection.Projection1OfEntityWithProjection;
import entities.simple.projection.Projection2OfEntityWithProjection;
import entities.simple.projection.ProjectionFullOfEntityWithProjection;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Test;

public class TestEntityWithProjection extends TransactionalSetup {

    @Test
    public void testProjection() {

        EntityWithProjection entity = new EntityWithProjection();
        entity.setId(1);
        entity.setName("name");
        entity.setValue(2);
        em.persist(entity);
        flushAndClear();

        Projection1OfEntityWithProjection projection1 = em.createQuery("select new entities.simple.projection.Projection1OfEntityWithProjection(e.id,e.name) from EntityWithProjection e where e.id = 1", Projection1OfEntityWithProjection.class).getSingleResult();
        Assert.assertEquals(entity.getId(), projection1.getId());
        Assert.assertEquals(entity.getName(), projection1.getName());

        Projection2OfEntityWithProjection projection2 = em.createQuery("select new entities.simple.projection.Projection2OfEntityWithProjection(e.id,e.value) from EntityWithProjection e where e.id = 1", Projection2OfEntityWithProjection.class).getSingleResult();
        Assert.assertEquals(entity.getId(), projection2.getId());
        Assert.assertEquals(entity.getValue(), projection2.getValue());

        ProjectionFullOfEntityWithProjection projectionFull = em.createQuery("select new entities.simple.projection.ProjectionFullOfEntityWithProjection(e.id,e.name,e.value) from EntityWithProjection e where e.id = 1", ProjectionFullOfEntityWithProjection.class).getSingleResult();
        Assert.assertEquals(entity.getId(), projectionFull.getId());
        Assert.assertEquals(entity.getName(), projectionFull.getName());
        Assert.assertEquals(entity.getValue(), projectionFull.getValue());
    }

}


package relationships.embedded;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import javax.persistence.Embeddable;
import java.util.Date;

public class TestRemoveEmbedded extends TransactionalSetup {

    private EntityWithEmbeddable model = buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    private EntityWithEmbeddable buildModel() {
        EntityWithEmbeddable entity = new EntityWithEmbeddable();
        entity.setId(1);
        entity.setEmbedded(new EmbeddableBean());
        entity.getEmbedded().setName("name " + entity.getId());
        entity.getEmbedded().setCreationDate(new Date());
        return entity;
    }

    /**
     * {@link Embeddable}s are not entities hence cannot be removed
     */
    @Test(expected = Exception.class)
    public void testRemoveEmbedded() {

        em.remove(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
        flushAndClear();

        Assert.assertNotNull(em.find(EntityWithEmbeddable.class, model.getId()));
        Assert.assertNull(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());

    }

    @Test
    public void testSetEmbeddedToNull() {

        EntityWithEmbeddable existing1 = em.find(EntityWithEmbeddable.class, model.getId());
        existing1.setEmbedded(null);
        flushAndClear();

        Assert.assertNotNull(em.find(EntityWithEmbeddable.class, model.getId()));
        Assert.assertNull(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
    }

}

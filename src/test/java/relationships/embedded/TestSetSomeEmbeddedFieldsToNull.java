package relationships.embedded;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.Date;

public class TestSetSomeEmbeddedFieldsToNull extends TransactionalSetup {

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

    @Test
    public void testSetSomeEmbeddedFieldsToNullLeadsToNotNullEmbedded() {

        EntityWithEmbeddable existing1 = em.find(EntityWithEmbeddable.class, model.getId());
        existing1.getEmbedded().setName(null);
        em.merge(existing1);
        flushAndClear();

        EntityWithEmbeddable existing2 = em.find(EntityWithEmbeddable.class, model.getId());

        // verify
        Assert.assertNotNull(existing2.getEmbedded());
        Assert.assertNull(existing2.getEmbedded().getName());
        Assert.assertNotNull(existing2.getEmbedded().getCreationDate());
        
    }
}

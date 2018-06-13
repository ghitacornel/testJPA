package relationships.embedded;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.Date;

public class TestSetAllEmbeddedFieldsToNull extends TransactionalSetup {

    private EntityWithEmbeddable entity;

    @Before
    public void before() {
        entity = new EntityWithEmbeddable();
        entity.setId(1);
        entity.setEmbedded(new EmbeddableBean());
        entity.getEmbedded().setName("name " + entity.getId());
        entity.getEmbedded().setCreationDate(new Date());
        persist(entity);
        flushAndClear();
    }

    @Test
    public void testSetAllEmbeddedFieldsToNullLeadsToNullEmbedded() {

        EntityWithEmbeddable existing = em.find(EntityWithEmbeddable.class, entity.getId());

        // set all embedded fields to null
        existing.getEmbedded().setName(null);
        existing.getEmbedded().setCreationDate(null);
        flushAndClear();

        Assert.assertNull(em.find(EntityWithEmbeddable.class, entity.getId()).getEmbedded());
    }
}

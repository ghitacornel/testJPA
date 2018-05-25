package relationships.embedded;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import java.util.Date;

public class TestSetAllEmbeddedFieldsToNull extends TransactionalSetup {

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
    public void testSetAllEmbeddedFieldsToNullLeadsToNullEmbedded() {

        EntityWithEmbeddable existing1 = em.find(EntityWithEmbeddable.class, model.getId());

        // set all embedded fields to null
        existing1.getEmbedded().setName(null);
        existing1.getEmbedded().setCreationDate(null);
        flushAndClear();

        Assert.assertNull(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
    }
}

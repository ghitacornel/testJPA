package relationships.embedded;

import relationships.embedded.EmbeddableBean;
import relationships.embedded.EntityWithEmbeddable;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class TestSetAllEmbeddedFieldsToNull extends TransactionalSetup {

    private EntityWithEmbeddable model = buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    private EntityWithEmbeddable buildModel() {
        EntityWithEmbeddable entitateCompusa = new EntityWithEmbeddable();
        entitateCompusa.setId(1);
        entitateCompusa.setEmbedded(new EmbeddableBean());
        entitateCompusa.getEmbedded().setName("name " + entitateCompusa.getId());
        entitateCompusa.getEmbedded().setCreationDate(new Date());
        return entitateCompusa;
    }

    @Test
    public void testSetAllEmbeddedFieldsToNullLeadsToNullEmbedded() {

        EntityWithEmbeddable existing1 = em.find(EntityWithEmbeddable.class, model.getId());

        // set all embedded fields to null
        existing1.getEmbedded().setName(null);
        existing1.getEmbedded().setCreationDate(null);

        em.merge(existing1);
        flushAndClear();

        Assert.assertNull(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
    }
}

package main.tests.relationships.embedded;

import entities.relationships.embedded.EmbeddableBean;
import entities.relationships.embedded.EntityWithEmbeddable;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class TestSetSomeEmbeddedFieldsToNull extends TransactionalSetup {

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

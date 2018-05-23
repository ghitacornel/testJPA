package relationships.embedded;

import relationships.embedded.EmbeddableBean;
import relationships.embedded.EntityWithEmbeddable;
import setup.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        EntityWithEmbeddable entitateCompusa = new EntityWithEmbeddable();
        entitateCompusa.setId(1);
        entitateCompusa.setEmbedded(new EmbeddableBean());
        entitateCompusa.getEmbedded().setName("name " + entitateCompusa.getId());
        entitateCompusa.getEmbedded().setCreationDate(new Date());
        return entitateCompusa;
    }

    /**
     * {@link Embeddable}s are not entities hence cannot be removed
     */
    @Test(expected = Exception.class)
    public void testRemoveEmbedded() {

        em.remove(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
        flushAndClear();

        Assert.assertNull(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
    }

    @Test
    public void testSetEmbeddedToNull() {

        EntityWithEmbeddable existing1 = em.find(EntityWithEmbeddable.class,
                model.getId());
        existing1.setEmbedded(null);
        em.merge(existing1);
        flushAndClear();

        Assert.assertNull(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
    }

}

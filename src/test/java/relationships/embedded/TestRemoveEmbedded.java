package relationships.embedded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import javax.persistence.Embeddable;
import java.util.Date;

public class TestRemoveEmbedded extends TransactionalSetup {

    private final EntityWithEmbeddable model = buildModel();

    @BeforeEach
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
    @Test
    public void testRemoveEmbedded() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            em.remove(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
            flushAndClear();
        });
    }

    @Test
    public void testSetEmbeddedToNull() {

        EntityWithEmbeddable existing1 = em.find(EntityWithEmbeddable.class, model.getId());
        existing1.setEmbedded(null);
        flushAndClear();

        Assertions.assertNotNull(em.find(EntityWithEmbeddable.class, model.getId()));
        Assertions.assertNull(em.find(EntityWithEmbeddable.class, model.getId()).getEmbedded());
    }

}

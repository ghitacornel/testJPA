package relationships.embedded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.Date;

public class TestSetSomeEmbeddedFieldsToNull extends TransactionalSetup {

    private EntityWithEmbeddable entity;

    @BeforeEach
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
    public void testSetSomeEmbeddedFieldsToNullLeadsToNotNullEmbedded() {

        EntityWithEmbeddable existing1 = em.find(EntityWithEmbeddable.class, entity.getId());
        existing1.getEmbedded().setName(null);
        flushAndClear();

        EntityWithEmbeddable existing2 = em.find(EntityWithEmbeddable.class, entity.getId());

        // verify
        Assertions.assertNotNull(existing2.getEmbedded());
        Assertions.assertNull(existing2.getEmbedded().getName());
        Assertions.assertNotNull(existing2.getEmbedded().getCreationDate());

    }
}

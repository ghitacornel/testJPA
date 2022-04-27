package entities.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityAttributeConverters extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithAttributeConverters.class);
    }

    @Test
    public void testCreateReadUpdateRead() {

        // create new entity
        EntityWithAttributeConverters entity1 = new EntityWithAttributeConverters();
        entity1.setId(1);
        entity1.setBooleanValue(true);
        entity1.setPassword("secret1");
        entity1.setCustomType(new CustomType());
        entity1.getCustomType().setX(1);
        entity1.getCustomType().setY("y1");

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,booleanValue,password from EntityWithAttributeConverters t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("Y", objects[1]);

                // text must match since we use a fixed algorithm and private key
                Assertions.assertEquals("TvZEM7lqd+QNaJKAz5/Gkw==", objects[2]);

            }
        }

        // verify
        EntityWithAttributeConverters entity2 = em.find(EntityWithAttributeConverters.class, entity1.getId());
        Assertions.assertNotNull(entity2);
        org.assertj.core.api.Assertions.assertThat(entity1).usingRecursiveComparison().isEqualTo(entity2);

        // update
        entity2.setBooleanValue(false);
        entity2.setPassword("secret2");
        entity1.setCustomType(new CustomType());
        entity1.getCustomType().setX(2);
        entity1.getCustomType().setY("y2");
        em.merge(entity2);
        flushAndClear();

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,booleanValue,password from EntityWithAttributeConverters t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals("N", objects[1]);

                // text must match since we use a fixed algorithm and private key
                Assertions.assertEquals("xDhSabqx5D/k0xImrZxPEg==", objects[2]);

            }
        }

        // verify
        EntityWithAttributeConverters entity3 = em.find(EntityWithAttributeConverters.class, entity1.getId());
        Assertions.assertNotNull(entity3);
        org.assertj.core.api.Assertions.assertThat(entity2).usingRecursiveComparison().isEqualTo(entity3);

    }

}

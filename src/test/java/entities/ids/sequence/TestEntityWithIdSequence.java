package entities.ids.sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityWithIdSequence extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithIdSequence.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithIdSequence model = new EntityWithIdSequence();
        Assertions.assertNull(model.getId());

        // persist
        em.persist(model);
        flushAndClear();

        // verify exactly 1 object was persisted
        List<EntityWithIdSequence> list = em.createQuery("select t from EntityWithIdSequence t", EntityWithIdSequence.class).getResultList();
        Assertions.assertEquals(1, list.size());
        EntityWithIdSequence existing = list.get(0);

        Assertions.assertNotNull(existing.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(model, existing);
    }

}

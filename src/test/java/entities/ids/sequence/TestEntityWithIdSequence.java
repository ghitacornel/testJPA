package entities.ids.sequence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityWithIdSequence extends TransactionalSetup {

    @Before
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithIdSequence.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithIdSequence model = new EntityWithIdSequence();
        Assert.assertNull(model.getId());

        // persist
        em.persist(model);
        flushAndClear();

        // verify exactly 1 object was persisted
        List<EntityWithIdSequence> list = em.createQuery("select t from EntityWithIdSequence t", EntityWithIdSequence.class).getResultList();
        Assert.assertEquals(1, list.size());
        EntityWithIdSequence existing = list.get(0);

        Assert.assertNotNull(existing.getId());// verify id was generated and populated
        ReflectionAssert.assertReflectionEquals(model, existing);
    }

}

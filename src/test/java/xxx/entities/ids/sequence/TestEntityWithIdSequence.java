package xxx.entities.ids.sequence;

import entities.ids.sequence.EntityWithIdSequence;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.List;

public class TestEntityWithIdSequence extends TransactionalSetup {

    private static final String SELECT_ALL = "select t from EntityWithIdSequence t";

    @Before
    public void before() {
        Assert.assertTrue(em.createQuery(SELECT_ALL).getResultList().isEmpty());
    }

    @Test
    public void test() {

        // create new entity
        EntityWithIdSequence model = new EntityWithIdSequence();
        Assert.assertNull(model.getId());

        // persist
        em.persist(model);
        flushAndClear();

        // verify
        List<EntityWithIdSequence> list = em.createQuery(SELECT_ALL, EntityWithIdSequence.class).getResultList();
        Assert.assertEquals(1, list.size());
        EntityWithIdSequence existing = list.get(0);

        Assert.assertNotNull(existing.getId());

        ReflectionAssert.assertReflectionEquals(model, existing);
    }

}

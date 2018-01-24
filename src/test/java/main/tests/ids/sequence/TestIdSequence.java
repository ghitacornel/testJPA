package main.tests.ids.sequence;

import entities.ids.sequence.EntityWithIdSequence;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.List;

public class TestIdSequence extends TransactionalSetup {

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
        Assert.assertNull(model.getDefaultInt());

        // persist
        em.persist(model);

        // mandatory clear cache (entity managers act as caches also)
        flushAndClear();

        // verify
        List<EntityWithIdSequence> list = em.createQuery(SELECT_ALL, EntityWithIdSequence.class).getResultList();
        Assert.assertEquals(1, list.size());
        EntityWithIdSequence existing = list.get(0);

        Assert.assertNotNull(existing.getId());

        // XXX hibernate + mysql looks to have an issue with default
        // XXX default in mysql has no effect
        // XXX not null mysql column forces hibernate to check for null values
        // XXX why checking it since a default exists
        Assert.assertNull(existing.getDefaultInt());

        ReflectionAssert.assertReflectionEquals(model, existing);
    }

}

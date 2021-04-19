package entities.cached;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestCached extends TransactionalSetup {

    CachedPerson cachedPerson1;
    CachedPerson cachedPerson2;
    CachedPerson cachedPerson3;

    @Before
    public void buildModel() {
        cachedPerson1 = new CachedPerson();
        cachedPerson1.setId(1);
        cachedPerson1.setName("name 1");
        cachedPerson2 = new CachedPerson();
        cachedPerson2.setId(2);
        cachedPerson2.setName("name 2");
        cachedPerson3 = new CachedPerson();
        cachedPerson3.setId(3);
        cachedPerson3.setName("name 3");
        em.persist(cachedPerson1);
        em.persist(cachedPerson2);
        em.persist(cachedPerson3);
        flushAndClear();
    }

    @Test
    public void testCacheHit() {
        System.out.println("START CACHE TEST ===============================   ");

        System.out.println(em.find(CachedPerson.class,cachedPerson1.getId()));
        flushAndClear();
        System.out.println(em.find(CachedPerson.class,cachedPerson1.getId()));
        flushAndClear();
        System.out.println(em.find(CachedPerson.class,cachedPerson1.getId()));
        flushAndClear();

        System.out.println("END CACHE TEST ===============================   ");
    }

}

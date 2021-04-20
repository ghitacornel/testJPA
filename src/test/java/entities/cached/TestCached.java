package entities.cached;

import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

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

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        Cache<Object, Object> cache = cacheManager.getCache("entities.cached.CachedPerson");
        System.out.println(cache);

        System.out.println(em.getEntityManagerFactory().getCache().contains(CachedPerson.class, cachedPerson1.getId()));
        System.out.println("START CACHE TEST ===============================   ");

        System.out.println(em.find(CachedPerson.class, cachedPerson1.getId()));
        System.out.println(em.getEntityManagerFactory().getCache().contains(CachedPerson.class, cachedPerson1.getId()));
        flushAndClear();

        System.out.println(em.find(CachedPerson.class, cachedPerson1.getId()));
        System.out.println(em.getEntityManagerFactory().getCache().contains(CachedPerson.class, cachedPerson1.getId()));
        flushAndClear();

        System.out.println(em.find(CachedPerson.class, cachedPerson1.getId()));
        System.out.println(em.getEntityManagerFactory().getCache().contains(CachedPerson.class, cachedPerson1.getId()));
        flushAndClear();

        System.out.println("END CACHE TEST ===============================   ");
    }

}

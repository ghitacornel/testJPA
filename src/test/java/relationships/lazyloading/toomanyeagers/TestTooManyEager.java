package relationships.lazyloading.toomanyeagers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

public class TestTooManyEager extends TransactionalSetup {

    TMECenter center;

    @BeforeEach
    public void setUp() {

        center = new TMECenter();
        center.setId(1);
        center.setName("center");

        TMELink1 link1 = new TMELink1();
        link1.setId(1);
        link1.setName("link 1");
        center.setLink1(link1);

        TMELink2 link2 = new TMELink2();
        link2.setId(2);
        link2.setName("link 2");
        center.setLink2(link2);

        TMELink3 link31 = new TMELink3();
        link31.setId(31);
        link31.setName("link 31");
        link31.setCenter(center);

        TMELink3 link32 = new TMELink3();
        link32.setId(32);
        link32.setName("link 32");
        link32.setCenter(center);

        TMELink4 link41 = new TMELink4();
        link41.setId(41);
        link41.setName("link 41");
        link41.setCenter(center);

        TMELink4 link42 = new TMELink4();
        link42.setId(42);
        link42.setName("link 42");
        link42.setCenter(center);

        persist(link1, link2, center, link31, link32, link41, link42);
        flushAndClear();

    }

    @Test
    public void testLoadOnMultipleSimpleRelationshipsAndOnlyOneCollectionRelationshipWorks() {

        TMECenter existing = em.find(TMECenter.class, center.getId());
        ReflectionAssert.assertReflectionEquals(center, existing, ReflectionComparatorMode.LENIENT_ORDER);

    }

    @Test
    public void testFailWhenLoadingMoreThanOneCollectionRelationships(){

        // for this test mark this relationship "relationships.lazyloading.toomanyeagers.TMECenter.links4" as EAGER

        TMECenter existing = em.find(TMECenter.class, center.getId());
        ReflectionAssert.assertReflectionEquals(center, existing, ReflectionComparatorMode.LENIENT_ORDER);

    }
}

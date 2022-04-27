package entities.special;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.List;

public class TestEntityWithEnums extends TransactionalSetup {

    @BeforeEach
    public void before() {
        verifyCorrespondingTableIsEmpty(EntityWithEnums.class);
    }

    @Test
    public void test() {

        // create new entity
        EntityWithEnums entity1 = new EntityWithEnums();
        entity1.setId(1);
        entity1.setEnum1(SimpleEnum.ONE);
        entity1.setEnum2(SimpleEnum.TWO);

        // persist
        em.persist(entity1);
        flushAndClear();

        // verify persist
        EntityWithEnums entity2 = em.find(EntityWithEnums.class, 1);
        Assertions.assertNotNull(entity2);
        org.assertj.core.api.Assertions.assertThat(entity1).usingRecursiveComparison().isEqualTo(entity2);

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,enum1,enum2 from EntityWithEnums t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals(SimpleEnum.ONE.name(), objects[1]);
                Assertions.assertEquals(SimpleEnum.TWO.ordinal(), objects[2]);
            }
        }

        // update
        entity2.setEnum1(SimpleEnum.THREE);
        entity2.setEnum2(SimpleEnum.FOUR);
        flushAndClear();

        // verify database state with a native query
        {
            List<Object[]> data = em.createNativeQuery("select id,enum1,enum2 from EntityWithEnums t").getResultList();
            Assertions.assertEquals(1, data.size());
            for (Object[] objects : data) {
                Assertions.assertEquals(1, objects[0]);
                Assertions.assertEquals(SimpleEnum.THREE.name(), objects[1]);
                Assertions.assertEquals(SimpleEnum.FOUR.ordinal(), objects[2]);
            }
        }

        // verify update
        EntityWithEnums entity3 = em.find(EntityWithEnums.class, 1);
        Assertions.assertNotNull(entity3);
        org.assertj.core.api.Assertions.assertThat(entity2).usingRecursiveComparison().isEqualTo(entity3);

    }

    @Test
    public void testFindByEnum() {

        EntityWithEnums entity1 = new EntityWithEnums();
        {
            entity1.setId(1);
            entity1.setEnum1(SimpleEnum.ONE);
            entity1.setEnum2(SimpleEnum.TWO);
            em.persist(entity1);
            flushAndClear();
        }

        {
            EntityWithEnums entity2 = new EntityWithEnums();
            entity2.setId(2);
            entity2.setEnum1(SimpleEnum.THREE);
            entity2.setEnum2(SimpleEnum.FOUR);
            em.persist(entity2);
            flushAndClear();
        }

        org.assertj.core.api.Assertions.assertThat(entity1).usingRecursiveComparison().isEqualTo(em.createQuery("select t from EntityWithEnums t where t.enum1 = entities.special.SimpleEnum.ONE").getSingleResult());
    }
}

package relationships.onetoone.bidirectional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestUpdateOneToOneWithCascade extends TransactionalSetup {

    private final A model = buildModel();

    private A buildModel() {

        A a = new A();
        a.setId(1);
        a.setName("this is a");

        B b = new B();
        b.setId(2);
        b.setName("this is b");
        b.setA(a);
        a.setB(b);

        return a;
    }

    @BeforeEach
    public void before() {
        em.persist(model);
        flushAndClear();
    }

    @Test
    public void testUpdateRelationshipToANewChildAndRemovalOfOrphan() {

        A existingA1 = em.find(A.class, model.getId());
        {

            // set new name to parent
            existingA1.setName("new name a");

            // create new child
            B b = new B();
            b.setId(4);
            b.setName("new name b");

            // establish new relationships
            b.setA(existingA1);
            existingA1.setB(b);

        }

        flushAndClear();

        A existingA2 = em.find(A.class, model.getId());
        org.assertj.core.api.Assertions.assertThat(existingA1).usingRecursiveComparison().isEqualTo(existingA2);
        org.assertj.core.api.Assertions.assertThat(existingA1.getB()).usingRecursiveComparison().isEqualTo(existingA2.getB());

        // test old B is removed
        Assertions.assertEquals(1, em.createQuery("select t from B t").getResultList().size());

    }

    @Test
    public void testMergeRelationshipToANewChildAndRemovalOfOrphan() {

        A newVersion = new A();
        newVersion.setId(1);
        {
            newVersion.setName("new name a");

            // create new child
            B b = new B();
            b.setId(4);
            b.setName("new name b");

            // establish new relationships
            b.setA(newVersion);
            newVersion.setB(b);

        }
        em.merge(newVersion);
        flushAndClear();

        A existingA2 = em.find(A.class, model.getId());
        org.assertj.core.api.Assertions.assertThat(newVersion).usingRecursiveComparison().isEqualTo(existingA2);
        org.assertj.core.api.Assertions.assertThat(newVersion.getB()).usingRecursiveComparison().isEqualTo(existingA2.getB());

        // test old B is removed
        Assertions.assertEquals(1, em.createQuery("select t from B t").getResultList().size());

    }
}

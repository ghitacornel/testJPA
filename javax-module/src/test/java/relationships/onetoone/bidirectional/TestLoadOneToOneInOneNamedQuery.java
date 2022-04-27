package relationships.onetoone.bidirectional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestLoadOneToOneInOneNamedQuery extends TransactionalSetup {

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
    public void testLoadParentAndChildInOneSingleQuery() {

        A existing = em.createQuery("select t from A t join fetch t.b where t.id = :id", A.class)
                .setParameter("id", 1)
                .getSingleResult();
        flushAndClear();

        org.assertj.core.api.Assertions.assertThat(model).usingRecursiveComparison().isEqualTo(existing);
        org.assertj.core.api.Assertions.assertThat(model.getB()).usingRecursiveComparison().isEqualTo(existing.getB());

    }
}

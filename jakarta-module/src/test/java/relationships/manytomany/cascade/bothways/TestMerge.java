package relationships.manytomany.cascade.bothways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestMerge extends TransactionalSetup {

    CascadeBothWaysM m;
    CascadeBothWaysN n;
    CascadeBothWaysN nUnaffected;

    @BeforeEach
    public void buildModel() {

        m = new CascadeBothWaysM();
        m.setId(1);
        m.setName("m 1 name");

        n = new CascadeBothWaysN();
        n.setId(1);
        n.setName("n 1 name");

        n.getListWithMs().add(m);
        m.getListWithNs().add(n);

        {// this is tested altogether via strict reflections
            nUnaffected = new CascadeBothWaysN();
            nUnaffected.setId(111);
            nUnaffected.setName("n 1 name");
            m.getListWithNs().add(nUnaffected);
            nUnaffected.getListWithMs().add(m);
        }

        em.persist(m);
        flushAndClear();

    }

    @Test
    public void testMerge() {


        CascadeBothWaysM m1Updated = new CascadeBothWaysM();
        m1Updated.setId(1);
        m1Updated.setName("m 1 new name");

        CascadeBothWaysN n1Updated = new CascadeBothWaysN();
        n1Updated.setId(1);
        n1Updated.setName("n 1 new name");

        CascadeBothWaysN n2New = new CascadeBothWaysN();
        n2New.setId(2);
        n2New.setName("n 2 new name");

        // ensure links are set
        m1Updated.getListWithNs().add(n1Updated);
        m1Updated.getListWithNs().add(n2New);
        n1Updated.getListWithMs().add(m1Updated);
        n2New.getListWithMs().add(m1Updated);

        // merge and expect cascade
        em.merge(m1Updated);
        flushAndClear();

        // verify
        {// alter the initial model to match expectations

            m.setName("m 1 new name");
            m.getListWithNs().add(n2New);
            m.getListWithNs().remove(nUnaffected);

            n.setName("n 1 new name");

            nUnaffected.getListWithMs().clear();

        }

        org.assertj.core.api.Assertions.assertThat(m).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysM.class, m.getId()));
        org.assertj.core.api.Assertions.assertThat(n).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysN.class, n.getId()));
        org.assertj.core.api.Assertions.assertThat(n2New).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysN.class, n2New.getId()));

        // the unaffected is not removed, only the links between entities are removed
        org.assertj.core.api.Assertions.assertThat(nUnaffected).usingRecursiveComparison().isEqualTo(em.find(CascadeBothWaysN.class, nUnaffected.getId()));

    }

}
package queries.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import relationships.onetomany.strict.OTOMStrictChild;
import relationships.onetomany.strict.OTOMStrictParent;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.List;

public class TestFindByEmptyCollection extends TransactionalSetup {

    OTOMStrictParent parent1;

    private List<OTOMStrictParent> buildModel() {
        List<OTOMStrictParent> list = new ArrayList<>();
        {
            parent1 = new OTOMStrictParent();
            parent1.setId(1);
            parent1.setName("parent 1");
            list.add(parent1);
        }
        {
            OTOMStrictParent parent2 = new OTOMStrictParent();
            parent2.setId(2);
            parent2.setName("parent 2");

            OTOMStrictChild child = new OTOMStrictChild();
            child.setId(1);
            child.setName("child 1");
            parent2.getChildren().add(child);
            child.setParent(parent2);
            list.add(parent2);
        }
        return list;
    }

    @BeforeEach
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    @Test
    public void testFindByEmptyCollection() {

        OTOMStrictParent existing = em.createQuery("select t from OTOMStrictParent t where t.children IS EMPTY", OTOMStrictParent.class).getSingleResult();
        ReflectionAssert.assertReflectionEquals(parent1, existing);

    }

}

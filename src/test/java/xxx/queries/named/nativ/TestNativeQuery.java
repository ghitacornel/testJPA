package xxx.queries.named.nativ;

import entities.simple.Entity;
import main.tests.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.List;

public class TestNativeQuery extends TransactionalSetup {

    @Before
    public void before() {
        persist(buildModel());
        flushAndClear();
    }

    private List<Entity> buildModel() {
        List<Entity> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Entity entity = new Entity();
            entity.setId(i);
            entity.setName("name " + i);
            list.add(entity);
        }
        return list;
    }

    @Test
    public void testWithNamedParameters() {

        List<Entity> list = em.createNamedQuery("Entity.findByNameNative", Entity.class).setParameter("name", "%1%").getResultList();

        List<Entity> expected = new ArrayList<>();
        expected.add(buildModel().get(0));
        ReflectionAssert.assertReflectionEquals(expected, list);
    }

    @Test
    public void testWithOrderParameters() {

        Entity entity = em.createNamedQuery("Entity.findByIdNative", Entity.class).setParameter(1, 1).getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(0), entity);

    }

    @Test
    public void testNamedQueryDefinedSeparately() {

        Entity entity = em.createNamedQuery("Entity.findByExactNameNative", Entity.class).setParameter(1, "name 2").getSingleResult();
        ReflectionAssert.assertReflectionEquals(buildModel().get(1), entity);

    }

}

package relationships.embedded;

import entities.relationships.embedded.EmbeddableBean;
import entities.relationships.embedded.EntityWithEmbeddable;
import setup.TransactionalSetup;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestSelectAll extends TransactionalSetup {

    private List<EntityWithEmbeddable> model = buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    private List<EntityWithEmbeddable> buildModel() {
        List<EntityWithEmbeddable> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            EntityWithEmbeddable entitateCompusa = new EntityWithEmbeddable();
            entitateCompusa.setId(i);
            entitateCompusa.setEmbedded(new EmbeddableBean());
            entitateCompusa.getEmbedded().setName("name " + i);
            entitateCompusa.getEmbedded().setCreationDate(new Date());
            list.add(entitateCompusa);
        }
        return list;
    }

    @Test
    public void test() {

        List<EntityWithEmbeddable> existing = em.createQuery(
                "select t from EntityWithEmbeddable t order by id",
                EntityWithEmbeddable.class).getResultList();

        ReflectionAssert.assertReflectionEquals(model, existing);
    }
}

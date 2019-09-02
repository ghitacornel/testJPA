package relationships.embedded;

import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestSelectWithEmbeddable extends TransactionalSetup {

    private List<EntityWithEmbeddable> model = buildModel();

    @Before
    public void before() {
        persist(model);
        flushAndClear();
    }

    private List<EntityWithEmbeddable> buildModel() {
        List<EntityWithEmbeddable> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            EntityWithEmbeddable entity = new EntityWithEmbeddable();
            entity.setId(i);
            entity.setEmbedded(new EmbeddableBean());
            entity.getEmbedded().setName("name " + i);
            entity.getEmbedded().setCreationDate(new Date());
            list.add(entity);
        }
        return list;
    }

    @Test
    public void testSelectAll() {

        List<EntityWithEmbeddable> existing = em.createQuery("select t from EntityWithEmbeddable t order by id", EntityWithEmbeddable.class).getResultList();
        ReflectionAssert.assertReflectionEquals(model, existing);

    }

    @Test
    public void testSelectAllEmbeddable() {

        List<EmbeddableBean> existing = em.createQuery("select t.embedded from EntityWithEmbeddable t order by id", EmbeddableBean.class).getResultList();

        List<EmbeddableBean> embeddableBeans = new ArrayList<>();
        for (EntityWithEmbeddable entityWithEmbeddable : model) {
            embeddableBeans.add(entityWithEmbeddable.getEmbedded());
        }

        ReflectionAssert.assertReflectionEquals(embeddableBeans, existing);

    }

    @Test
    public void testFindByEmbeddable() {

        EmbeddableBean embedded = new EmbeddableBean();
        embedded.setName(model.get(0).getEmbedded().getName());
        embedded.setCreationDate(model.get(0).getEmbedded().getCreationDate());

        EntityWithEmbeddable existing = em.createQuery("select t from EntityWithEmbeddable t where t.embedded = :embedded", EntityWithEmbeddable.class).setParameter("embedded", embedded).getSingleResult();

        ReflectionAssert.assertReflectionEquals(model.get(0), existing);

    }
}

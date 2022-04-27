package relationships.embedded;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestRemoveByEmbeddable extends TransactionalSetup {

    private final List<EntityWithEmbeddable> model = buildModel();

    @BeforeEach
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
    public void testRemoveByEmbeddable() {

        EmbeddableBean embedded = new EmbeddableBean();
        embedded.setName(model.get(0).getEmbedded().getName());
        embedded.setCreationDate(model.get(0).getEmbedded().getCreationDate());

        em.createQuery("delete from EntityWithEmbeddable t where t.embedded = :embedded").setParameter("embedded", embedded).executeUpdate();

        org.assertj.core.api.Assertions.assertThat(model.subList(1, model.size())).usingRecursiveComparison().isEqualTo(em.createQuery("select t from EntityWithEmbeddable t", EntityWithEmbeddable.class).getResultList());

    }
}

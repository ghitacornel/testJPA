package relationships.embedded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

import jakarta.persistence.Persistence;
import java.util.Date;

public class TestCRUD extends TransactionalSetup {

    @BeforeEach
    public void setUp() {
        verifyTableIsEmpty("EWE_Names");
        verifyTableIsEmpty("EntityWithEmbeddable_relatedEmbedded");
    }

    @Test
    public void testCRUD() {

        // create model
        EntityWithEmbeddable model = new EntityWithEmbeddable();
        model.setId(1);

        {
            EmbeddableBean embeddableBean = new EmbeddableBean();
            embeddableBean.setName("name first");
            embeddableBean.setCreationDate(new Date());
            model.setEmbedded(embeddableBean);
        }
        {
            model.getNames().add("one");
            model.getNames().add("two");
            model.getNames().add("three");
        }
        {
            EmbeddableBean embeddableBean1 = new EmbeddableBean();
            embeddableBean1.setName("name1");
            embeddableBean1.setCreationDate(new Date());
            model.getRelatedEmbedded().add(embeddableBean1);
            EmbeddableBean embeddableBean2 = new EmbeddableBean();
            embeddableBean2.setName("name2");
            embeddableBean2.setCreationDate(new Date());
            model.getRelatedEmbedded().add(embeddableBean2);
            EmbeddableBean embeddableBean3 = new EmbeddableBean();
            embeddableBean3.setName("name3");
            embeddableBean3.setCreationDate(new Date());
            model.getRelatedEmbedded().add(embeddableBean3);
        }

        // persist
        em.persist(model);
        flushAndClear();

        // test persist
        org.assertj.core.api.Assertions.assertThat(model).usingRecursiveComparison().isEqualTo(em.find(EntityWithEmbeddable.class, model.getId()));
        flushAndClear();

        // test lazy loading
        {
            EntityWithEmbeddable existing = em.find(EntityWithEmbeddable.class, model.getId());
            Assertions.assertFalse(Persistence.getPersistenceUtil().isLoaded(existing.getNames()));
            Assertions.assertFalse(Persistence.getPersistenceUtil().isLoaded(existing.getRelatedEmbedded()));
        }

        // update
        EntityWithEmbeddable existingModel = em.find(EntityWithEmbeddable.class, model.getId());
        existingModel.getNames().remove("three");
        existingModel.getNames().add("four");
        {
            EmbeddableBean toRemove = null;
            for (EmbeddableBean embeddableBean : existingModel.getRelatedEmbedded()) {
                if (embeddableBean.getName().equals("name3")) {
                    toRemove = embeddableBean;
                    break;
                }
            }
            existingModel.getRelatedEmbedded().remove(toRemove);
            EmbeddableBean toUpdate = existingModel.getRelatedEmbedded().stream().filter(embeddableBean -> embeddableBean.getName().equals("name2")).findFirst().orElse(null);
            toUpdate.setName("name2updated");
        }
        flushAndClear();

        // verify update
        {// adjust model to reflect changes
            model.getNames().remove("three");
            model.getNames().add("four");
            EmbeddableBean toRemove = null;
            for (EmbeddableBean embeddableBean : model.getRelatedEmbedded()) {
                if (embeddableBean.getName().equals("name3")) {
                    toRemove = embeddableBean;
                    break;
                }
            }
            model.getRelatedEmbedded().remove(toRemove);
            EmbeddableBean toUpdate = model.getRelatedEmbedded().stream().filter(embeddableBean -> embeddableBean.getName().equals("name2")).findFirst().orElse(null);
            toUpdate.setName("name2updated");
        }
        org.assertj.core.api.Assertions.assertThat(model).usingRecursiveComparison().isEqualTo(em.find(EntityWithEmbeddable.class, model.getId()));
        flushAndClear();

        // remove
        em.remove(em.find(EntityWithEmbeddable.class, model.getId()));
        flushAndClear();

        // verify remove
        Assertions.assertNull(em.find(EntityWithEmbeddable.class, model.getId()));

    }
}

package relationships.embedded;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import setup.TransactionalSetup;

import javax.persistence.Persistence;
import java.util.Date;

public class TestCRUD extends TransactionalSetup {

    @Before
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
        ReflectionAssert.assertReflectionEquals(model, em.find(EntityWithEmbeddable.class, model.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        flushAndClear();

        // test lazy loading
        {
            EntityWithEmbeddable existing = em.find(EntityWithEmbeddable.class, model.getId());
            Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existing.getNames()));
            Assert.assertFalse(Persistence.getPersistenceUtil().isLoaded(existing.getRelatedEmbedded()));
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
            EmbeddableBean toUpdate = null;
            for (EmbeddableBean embeddableBean : existingModel.getRelatedEmbedded()) {
                if (embeddableBean.getName().equals("name2")) {
                    toUpdate = embeddableBean;
                    break;
                }
            }
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
            EmbeddableBean toUpdate = null;
            for (EmbeddableBean embeddableBean : model.getRelatedEmbedded()) {
                if (embeddableBean.getName().equals("name2")) {
                    toUpdate = embeddableBean;
                    break;
                }
            }
            toUpdate.setName("name2updated");
        }
        ReflectionAssert.assertReflectionEquals(model, em.find(EntityWithEmbeddable.class, model.getId()), ReflectionComparatorMode.LENIENT_ORDER);
        flushAndClear();

        // remove
        em.remove(em.find(EntityWithEmbeddable.class, model.getId()));
        flushAndClear();

        // verify remove
        Assert.assertNull(em.find(EntityWithEmbeddable.class, model.getId()));

    }
}

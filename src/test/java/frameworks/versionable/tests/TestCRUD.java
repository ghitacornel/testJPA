package frameworks.versionable.tests;

import frameworks.versionable.tests.model.Model;
import frameworks.versionable.tests.model.ModelVersion;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class TestCRUD extends TransactionalSetup {

    @Test
    public void testCRUD_Model() {

        // insert
        Model model1 = new Model();
        model1.setId(1);
        model1.setName("name1");
        em.persist(model1);
        flushAndClear();

        // test insert
        ReflectionAssert.assertReflectionEquals(model1, em.find(frameworks.versionable.tests.model.Model.class, model1.getId()));
        flushAndClear();

        // update
        Model model2 = em.find(Model.class, model1.getId());
        model2.setName("name2");
        em.persist(model2);
        flushAndClear();

        // test update
        ReflectionAssert.assertReflectionEquals(model2, em.find(frameworks.versionable.tests.model.Model.class, model2.getId()));
        flushAndClear();

        // delete
        em.remove(em.find(frameworks.versionable.tests.model.Model.class, model2.getId()));
        flushAndClear();

        // test delete
        Assert.assertNull(em.find(frameworks.versionable.tests.model.Model.class, model2.getId()));

    }

    @Test(expected = RuntimeException.class)
    public void testCRUD_Version() {

        // insert
        ModelVersion model1 = new ModelVersion();
        model1.setDefinition("definition1");
        Assert.assertNull(model1.getStartDate());
        em.persist(model1);
        flushAndClear();

        // test insert
        Assert.assertNotNull(model1.getStartDate());
        ReflectionAssert.assertReflectionEquals(model1, em.find(frameworks.versionable.tests.model.ModelVersion.class, model1.getId()));
        flushAndClear();

        // update
        ModelVersion model2 = em.find(ModelVersion.class, model1.getId());
        model2.setDefinition("definition2");
        em.persist(model2);
        flushAndClear();

        // test update
        ReflectionAssert.assertReflectionEquals(model2, em.find(frameworks.versionable.tests.model.ModelVersion.class, model2.getId()));
        flushAndClear();

        // delete
        try {
            em.remove(em.find(frameworks.versionable.tests.model.ModelVersion.class, model2.getId()));
            flushAndClear();
        } catch (Exception e) {
            Assert.assertEquals("this exception will roll back the current transaction thus preventing accidental delete", e.getMessage());
            throw e;
        }

        Assert.fail("exception expected");

    }
}

package frameworks.auditable.tests;

import frameworks.auditable.tests.model.AuditableModel;
import main.tests.TransactionalSetup;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

public class TestCRUD extends TransactionalSetup {

    @Test
    public void testCRU() {

        // insert
        AuditableModel auditableModel = new AuditableModel();
        auditableModel.setName("name 0");
        Assert.assertNull(auditableModel.getCreationDate());
        Assert.assertNull(auditableModel.getCreator());
        Assert.assertNull(auditableModel.getLastUpdateDate());
        Assert.assertNull(auditableModel.getUpdater());
        em.persist(auditableModel);
        flushAndClear();

        // test insert
        Assert.assertNotNull(auditableModel.getCreationDate());
        Assert.assertNotNull(auditableModel.getCreator());
        Assert.assertNull(auditableModel.getLastUpdateDate());
        Assert.assertNull(auditableModel.getUpdater());
        ReflectionAssert.assertReflectionEquals(auditableModel, em.find(AuditableModel.class, auditableModel.getId()));
        flushAndClear();

        // update
        auditableModel = em.find(AuditableModel.class, auditableModel.getId());
        auditableModel.setName("name 1");
        em.merge(auditableModel);
        flushAndClear();

        // test update
        Assert.assertNotNull(auditableModel.getCreationDate());
        Assert.assertNotNull(auditableModel.getCreator());
        Assert.assertNotNull(auditableModel.getLastUpdateDate());
        Assert.assertNotNull(auditableModel.getUpdater());
        ReflectionAssert.assertReflectionEquals(auditableModel, em.find(AuditableModel.class, auditableModel.getId()));
        flushAndClear();

    }

    @Test(expected = RuntimeException.class)
    public void testDelete() {

        // insert
        AuditableModel auditableModel = new AuditableModel();
        auditableModel.setName("name 0");
        em.persist(auditableModel);
        flushAndClear();

        // remove
        try {
            em.remove(em.find(AuditableModel.class, auditableModel.getId()));
            flushAndClear();
        } catch (RuntimeException e) {
            Assert.assertEquals("this exception will roll back the current transaction thus preventing accidental delete", e.getMessage());
            throw e;
        }

        Assert.fail("exception must be raised");

    }
}

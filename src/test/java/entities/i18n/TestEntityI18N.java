package entities.i18n;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import setup.TransactionalSetup;

public class TestEntityI18N extends TransactionalSetup {

    @Before
    public void verifyDatabaseState() {
        verifyCorrespondingTableIsEmpty(EntityI18N.class);
    }

    @Test
    public void testPersistI18N() {

        EntityI18N entityI18N = new EntityI18N();
        entityI18N.setId(1);
        entityI18N.setValue1("français");
        entityI18N.setValue2("Möbelträgerfüße");
        entityI18N.setValue3("русский");
        entityI18N.setValue4("日本");
        em.persist(entityI18N);
        flushAndClear();

        Assert.assertEquals("français", em.find(EntityI18N.class, entityI18N.getId()).getValue1());
        Assert.assertEquals("Möbelträgerfüße", em.find(EntityI18N.class, entityI18N.getId()).getValue2());
        Assert.assertEquals("русский", em.find(EntityI18N.class, entityI18N.getId()).getValue3());
        Assert.assertEquals("日本", em.find(EntityI18N.class, entityI18N.getId()).getValue4());
    }
}

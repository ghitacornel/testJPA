package entities.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.TransactionalSetup;

public class TestEntityI18N extends TransactionalSetup {

    @BeforeEach
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

        Assertions.assertEquals("français", em.find(EntityI18N.class, entityI18N.getId()).getValue1());
        Assertions.assertEquals("Möbelträgerfüße", em.find(EntityI18N.class, entityI18N.getId()).getValue2());
        Assertions.assertEquals("русский", em.find(EntityI18N.class, entityI18N.getId()).getValue3());
        Assertions.assertEquals("日本", em.find(EntityI18N.class, entityI18N.getId()).getValue4());
    }
}

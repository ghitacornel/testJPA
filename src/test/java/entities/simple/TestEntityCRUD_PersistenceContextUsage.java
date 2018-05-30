package entities.simple;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import setup.TransactionalSetup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestEntityCRUD_PersistenceContextUsage extends TransactionalSetup {

    Connection connection;

    // TODO check that the connection is the same as the JPA uses for the current test
    Transaction currentTransaction;

    @Before
    public void beforeTestObtainConnectionAndTransaction() throws Exception {

        Session session = em.unwrap(Session.class);
        currentTransaction = session.getTransaction();
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
        connection = sfi.getJdbcServices().getBootstrapJdbcConnectionAccess().obtainConnection();

        verifyCorrespondingTableIsEmpty(Entity.class);
    }

    private void VERIFY_NO_DATA_IS_WRITTEN_IN_THE_DATABASE_WITHIN_THE_SAME_TRANSACTION_DUE_TO_PERSISTENCE_CONTEXT() throws Exception {

        // ensure we use the same transaction
        Assert.assertSame(currentTransaction, em.getTransaction());

        // use plain old JDBC for test
        List<Object[]> data = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select * from SimpleEntity")) {
                while (resultSet.next()) {
                    Object o_0 = resultSet.getObject(0);
                    Object o_1 = resultSet.getObject(1);
                    Object o_2 = resultSet.getObject(2);
                    data.add(new Object[]{o_0, o_1, o_2});
                }
            }
        }
        if (!data.isEmpty()) {
            Assert.fail("something was written in the database");
        }

    }

    @After
    public void afterTestReleaseConnectionAndTransaction() throws Exception {
        Session session = em.unwrap(Session.class);
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
        sfi.getJdbcServices().getBootstrapJdbcConnectionAccess().releaseConnection(connection);
        currentTransaction.markRollbackOnly();
    }

    @Test
    public void test() throws Exception {

        // create new entity
        Entity entity1 = new Entity();
        entity1.setId(1);
        entity1.setName("name");

        VERIFY_NO_DATA_IS_WRITTEN_IN_THE_DATABASE_WITHIN_THE_SAME_TRANSACTION_DUE_TO_PERSISTENCE_CONTEXT();

        // persist
        em.persist(entity1);
        // flushAndClear();//  this time no flush and clear

        // verify persist
        Entity entity2 = em.find(Entity.class, entity1.getId());
        Assert.assertNotNull(entity2);
        ReflectionAssert.assertReflectionEquals(entity1, entity2);

        VERIFY_NO_DATA_IS_WRITTEN_IN_THE_DATABASE_WITHIN_THE_SAME_TRANSACTION_DUE_TO_PERSISTENCE_CONTEXT();

        // update
        entity2.setName("new name");
        entity2.setValue(12);
        // flushAndClear();//  this time no flush and clear

        // verify update
        Entity entity3 = em.find(Entity.class, entity1.getId());
        Assert.assertNotNull(entity3);
        ReflectionAssert.assertReflectionEquals(entity2, entity3);

        VERIFY_NO_DATA_IS_WRITTEN_IN_THE_DATABASE_WITHIN_THE_SAME_TRANSACTION_DUE_TO_PERSISTENCE_CONTEXT();

        // remove
        Entity entity4 = em.find(Entity.class, entity1.getId());
        Assert.assertNotNull(entity4);
        em.remove(entity4);
        // flushAndClear();//  this time no flush and clear

        // verify remove
        Assert.assertNull(em.find(Entity.class, entity1.getId()));

        VERIFY_NO_DATA_IS_WRITTEN_IN_THE_DATABASE_WITHIN_THE_SAME_TRANSACTION_DUE_TO_PERSISTENCE_CONTEXT();

    }

}


package entities.simple;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import setup.Setup;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class TestJDBCvsJPA extends Setup {

    private DataSource dataSource;
    {
        dataSource = new JDBCDataSource();
        ((JDBCDataSource) dataSource).setURL("jdbc:hsqldb:mem:testdb");
    }

    @Before
    public void addSomeData() throws Exception {
        dataSource.getConnection().createStatement().execute("delete from SimpleEntity;");
        dataSource.getConnection().createStatement().execute("insert into SimpleEntity(id,name,nullableValue) values (1,'nume1',null);");
        dataSource.getConnection().createStatement().execute("insert into SimpleEntity(id,name,nullableValue) values (2,'nume2',11);");
        dataSource.getConnection().createStatement().execute("insert into SimpleEntity(id,name,nullableValue) values (3,'nume3',22);");
    }

    @After
    public void clearAll() throws Exception {
        dataSource.getConnection().createStatement().execute("delete from SimpleEntity;");
    }

    @Test
    public void testSelectAllJDBC() throws Exception {

        Connection connection = dataSource.getConnection();//1
        Statement statement = connection.createStatement();//2
        ResultSet resultSet = statement.executeQuery("select id,name,nullableValue from SimpleEntity");//3

        while (resultSet.next()) {
            System.out.print(resultSet.getInt(1) + " ");
            System.out.print(resultSet.getString(2) + " ");
            System.out.print(resultSet.getObject(3) + " ");
            System.out.println();
        }
    }

    @Test
    public void testSelectAllJPA() throws Exception {

        EntityManager entityManager = entityManagerFactory.createEntityManager();//1
        TypedQuery<Entity> query = entityManager.createQuery("select t from Entity t", Entity.class);//2
        List<Entity> list = query.getResultList();//3

        for (Entity entity : list) {
            System.out.print(entity.getId() + " ");
            System.out.print(entity.getName() + " ");
            System.out.print(entity.getValue() + " ");
            System.out.println();
        }

    }

}

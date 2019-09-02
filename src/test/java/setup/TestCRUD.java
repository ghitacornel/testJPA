package setup;

import model.Person;
import org.hsqldb.util.DatabaseManagerSwing;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestCRUD {

    public static void main(String[] argsu) {

        final Runnable r = () -> {
            System.out.println("LAUNCHING HSQL DBMANAGERSWING");
            final String[] args = {"--url", "jdbc:hsqldb:mem:testdb", "--noexit"};
            try {
                DatabaseManagerSwing.main(args);
            } catch (final Exception e) {
                System.out.println("Could not start hsqldb database manager GUI: " + e.getMessage());
            }
        };
        new Thread(r).start();
    }

    @Test
    public void testCRUD() throws Exception {

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb","SA","SA");

        connection.createStatement().execute("SET DATABASE EVENT LOG SQL LEVEL 3;");
        connection.createStatement().execute("CREATE TABLE Person(id INTEGER, name VARCHAR(20));");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Person(id,name) VALUES (?,?);");
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "ion");
        preparedStatement.executeUpdate();
        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, "maria");
        preparedStatement.executeUpdate();
        preparedStatement.setInt(1, 3);
        preparedStatement.setString(2, "gheorghe");
        preparedStatement.executeUpdate();

        List<Person> people = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT id , name FROM Person");
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Person person = new Person(id, name);
            people.add(person);
        }

        System.out.println(people);

        connection.close();

    }

}

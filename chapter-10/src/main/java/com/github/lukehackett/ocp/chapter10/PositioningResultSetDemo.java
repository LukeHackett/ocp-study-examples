package com.github.lukehackett.ocp.chapter10;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PositioningResultSetDemo extends JDBCBase {

    public static void main(String[] args) throws IOException, SQLException {
        PositioningResultSetDemo demo = new PositioningResultSetDemo();
        demo.executeSqlScript("zoo-schema.sql");
        demo.insertInitialData();
        demo.run();
    }

    public void insertInitialData() throws SQLException {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO species VALUES (1, 'African Elephant', 7.5)");
            statement.executeUpdate("INSERT INTO species VALUES (2, 'Zebra', 1.2)");

            statement.executeUpdate("INSERT INTO animals VALUES (1, 1, 'Elsa', '2001-05-06 02:15:00')");
            statement.executeUpdate("INSERT INTO animals VALUES (2, 2, 'Zelda', '2002-08-15 09:12:00.000')");
            statement.executeUpdate("INSERT INTO animals VALUES (3, 1, 'Ester', '2002-09-09 10:36:00.000')");
            statement.executeUpdate("INSERT INTO animals VALUES (4, 1, 'Eddie', '2010-06-08 01:24:00.000')");
            statement.executeUpdate("INSERT INTO animals VALUES (5, 2, 'Zoe', '2005-11-12 03:44:00.000')");
        }
    }

    public void run() {
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
        ) {

            ResultSet rs = statement.executeQuery("SELECT * FROM animals ORDER BY id");

            rs.next();

            rs.absolute(2);
            System.out.println(rs.getString("name") + " was born on " + rs.getTimestamp("date_born"));

            rs.relative(1);
            System.out.println(rs.getString("name") + " was born on " + rs.getTimestamp("date_born"));

            // When using relative with a negative number then TYPE_SCROLL_* must be used
            rs.relative(-2);
            System.out.println(rs.getString("name") + " was born on " + rs.getTimestamp("date_born"));

            rs.absolute(-1);
            System.out.println(rs.getString("name") + " was born on " + rs.getTimestamp("date_born"));

            rs.relative(-5);
            // raises SQLException: No data is available
            System.out.println(rs.getString("name") + " was born on " + rs.getTimestamp("date_born"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

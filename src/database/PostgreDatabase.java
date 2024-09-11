package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreDatabase implements IDB {
    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String URL = "jdbc:postgresql://localhost:5432/airline";
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, "postgres", "1245emer");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }
}
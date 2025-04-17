package javaMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "pass";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DB connected");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading JDBC Driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}

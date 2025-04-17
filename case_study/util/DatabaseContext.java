package hexaware.case_study.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseContext {
    private static final String URL = "jdbc:mysql://localhost:3306/rentaldb?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "pass";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

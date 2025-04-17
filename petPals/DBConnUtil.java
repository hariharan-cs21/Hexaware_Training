package hexaware.petPals;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnUtil {
    public static Connection getConnection(String fileName) throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream(fileName));
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");
        return DriverManager.getConnection(url, user, pass);
    }
}

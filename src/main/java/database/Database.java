package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final String PROPERTIES_FILE = "/database.properties";
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Properties props = new Properties();
            String envUrl = System.getenv("DB_URL");
            String envUser = System.getenv("DB_USER");
            String envPass = System.getenv("DB_PASS");
            if (envUrl != null && envUser != null && envPass != null) {
                URL = envUrl;
                USER = envUser;
                PASSWORD = envPass;
                System.out.println("Using database config from environment variables");
            } else {
                try (InputStream input = Database.class.getResourceAsStream(PROPERTIES_FILE)) {
                    if (input == null) {
                        throw new IOException("Properties file not found: " + PROPERTIES_FILE);
                    }
                    props.load(input);
                    URL = props.getProperty("dburl");
                    USER = props.getProperty("dbuser");
                    PASSWORD = props.getProperty("dbpass");
                    System.out.println("Using database config from properties file");
                }
            }
            if (URL == null || USER == null || PASSWORD == null) {
                throw new IllegalArgumentException("Missing required database properties");
            }
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database properties: " + e.getMessage());
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

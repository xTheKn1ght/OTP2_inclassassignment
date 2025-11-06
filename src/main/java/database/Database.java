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
        try (InputStream input = Database.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IOException("Properties file not found: " + PROPERTIES_FILE);
            }

            Properties props = new Properties();
            props.load(input);

            URL = props.getProperty("dburl");
            USER = props.getProperty("dbuser");
            PASSWORD = props.getProperty("dbpass");

            if (URL == null || USER == null || PASSWORD == null) {
                throw new IllegalArgumentException("Missing required database properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database properties: " + e.getMessage());
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

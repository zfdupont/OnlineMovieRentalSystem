package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
	private static ConnectionSingleton instance;
    private Connection connection;
    private final String url = "jdbc:mysql://your-database-url";
    private final String username = "username";
    private final String password = "password";

    private ConnectionSingleton() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Connection Failed: " + ex.getMessage());
        }
    }

    public static ConnectionSingleton getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConnectionSingleton();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

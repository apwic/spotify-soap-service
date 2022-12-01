package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    public static Connection connect() {
        String user = "root";
        String password = "root";
        String url = "jdbc:mysql://database/sepotipayi_soap";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ERROR: register JDBC Driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("ERROR: connection error", e);
        }
    }
}
